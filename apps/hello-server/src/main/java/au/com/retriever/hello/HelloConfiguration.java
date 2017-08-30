package au.com.retriever.hello;
/** Copyright (C) 2017 Retriever Communications
 *  
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class HelloConfiguration extends Configuration
{
	/* Defines the default value that is returned in a /hello response if no name parameter is provided */
	@NotEmpty
    private String defaultName;
	
	/* Defines the number of /hello requests the server can respond to before its health-checks fail */
	private Integer greetingLimit;
	
	/* Defines an identifier for the server which is added to the Server response header, which can be used 
	 * to determine which instance served a request when accessed through a load balancer. */
	private String identifier;
	
	@JsonProperty
	public String getDefaultName()
	{
		return this.defaultName;
	}
	
	@JsonProperty
	public void setDefaultName(String name)
	{
		this.defaultName = name;
	}
	
	@JsonProperty
	public Integer getGreetingLimit()
	{
		return this.greetingLimit;
	}
	
	@JsonProperty
	public void setGreetingLimit(Integer greetingLimit)
	{
		this.greetingLimit = greetingLimit;
	}
	
	@JsonProperty
	public String getIdentifier()
	{
		return this.identifier;
	}
	
	@JsonProperty
	public void setIdentifier(String serverName)
	{
		this.identifier = serverName;
	}
}
