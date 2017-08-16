package au.com.retriever.hello.rest.filters;
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

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.codahale.metrics.health.HealthCheck.Result;

import au.com.retriever.hello.healthchecks.GreetingLimitHealthCheck;
import au.com.retriever.hello.rest.representations.MessageRepresentation;


@Provider
public class GreetingLimitFilter implements ContainerRequestFilter
{
	private static final Integer TOO_MANY_REQUESTS_STATUS_CODE = 429;
	private static final String INTERNAL_SERVER_ERROR_MESSAGE = "The server has encountered an internal error and is unable to complete your request";
	
	private final GreetingLimitHealthCheck limitHealthCheck;
	
	public GreetingLimitFilter(GreetingLimitHealthCheck limitHealthCheck)
	{
		this.limitHealthCheck = limitHealthCheck;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException 
	{
		Result healthCheckResult;
		try
		{		
			 healthCheckResult = limitHealthCheck.check();
		} 
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new MessageRepresentation(INTERNAL_SERVER_ERROR_MESSAGE))
					.build());
		}
		
		// If the limit health check failed, then we have hit the request limit,
		// so return an error response instead of proceeding.
		if (!healthCheckResult.isHealthy())
		{
			throw new WebApplicationException(Response.status(TOO_MANY_REQUESTS_STATUS_CODE)
					.entity(new MessageRepresentation("The greeting limit has been exceeded!"))
					.build());
		}
	}
}
