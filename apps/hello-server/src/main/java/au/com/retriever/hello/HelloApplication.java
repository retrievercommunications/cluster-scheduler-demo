package au.com.retriever.hello;
/**	Copyright (C) 2017 Retriever Communications
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining
 *	a copy of this software and associated documentation files (the
 *	"Software"), to deal in the Software without restriction, including
 *	without limitation the rights to use, copy, modify, merge, publish,
 *	distribute, sublicense, and/or sell copies of the Software, and to
 *	permit persons to whom the Software is furnished to do so, subject to
 *	the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be
 *	included in all copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *	NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *	LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *	OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *	WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.concurrent.atomic.AtomicInteger;

import au.com.retriever.hello.healthchecks.GreetingLimitHealthCheck;
import au.com.retriever.hello.rest.filters.GreetingLimitFilter;
import au.com.retriever.hello.rest.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class HelloApplication extends Application<HelloConfiguration> 
{
	private static final String APP_NAME = "hello-app";
	
	private final AtomicInteger greetingCounter = new AtomicInteger();
	
	@Override
	public String getName() 
	{
		return APP_NAME;
	}
	
	@Override
	public void run(HelloConfiguration configuration, Environment environment) throws Exception 
	{
		Integer greetingLimit = configuration.getGreetingLimit();
		String defaultName = configuration.getDefaultName();
		
		GreetingLimitHealthCheck limitHealthCheck = new GreetingLimitHealthCheck(this.greetingCounter, greetingLimit);
		GreetingLimitFilter limitFilter = new GreetingLimitFilter(limitHealthCheck);
		HelloResource helloResource = new HelloResource(defaultName, this.greetingCounter);
		
		environment.jersey().register(limitFilter);
		environment.jersey().register(helloResource);
		environment.healthChecks().register("limit", limitHealthCheck);
	}
	
	public static void main(String[] args) throws Exception 
	{
		new HelloApplication().run(args);
	}
}
