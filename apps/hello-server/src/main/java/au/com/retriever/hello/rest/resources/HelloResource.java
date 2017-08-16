package au.com.retriever.hello.rest.resources;
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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import au.com.retriever.hello.rest.representations.MessageRepresentation;
import io.dropwizard.jersey.params.NonEmptyStringParam;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource
{
	private String defaultName;
	private AtomicInteger greetingCounter;

	public HelloResource(String defaultName, AtomicInteger greetingCounter)
	{
		this.defaultName = defaultName;
		this.greetingCounter = greetingCounter;
	}
	
	@GET
	public Response sayHello(@QueryParam("name") NonEmptyStringParam nameParam)
	{
		greetingCounter.getAndIncrement();
		
		String name = nameParam.get().orElse(this.defaultName);
		return Response.ok().entity(new MessageRepresentation("Hello, " + name + "!")).build();
	}
}
