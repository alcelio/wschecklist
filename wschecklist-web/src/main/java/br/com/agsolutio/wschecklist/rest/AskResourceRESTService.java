
package br.com.agsolutio.wschecklist.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.agsolutio.wschecklist.data.AskRepository;
import br.com.agsolutio.wschecklist.model.Ask;
import br.com.agsolutio.wschecklist.service.AskRegistration;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the ask
 * table.
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 * 
 * @since 15/06/2017
 */
@Path("/asks")
@RequestScoped
public class AskResourceRESTService {
	@Inject
	private Logger log;

	@Inject
	private AskRepository repository;

	@Inject
	AskRegistration registration;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ask> listAllAsks() {
		return repository.findAll();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Ask deleteById(@PathParam("id") long id) {
		Ask ask = repository.findById(id);
		if (ask == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return ask;

	}

	@GET
	@Path("/del/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteAskById(@PathParam("id") long id) {
		Ask ask = repository.findById(id);
		if (ask == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		registration.deleteById(id);
	}

	/**
	 * Creates a new {@link Ask} from the values provided. Will return a JAX-RS
	 * response with either 200 ok, or with a map of fields, and related errors.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAsk(Ask ask) {

		Response.ResponseBuilder builder = null;

		try {

			registration.register(ask);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}

	/**
	 * Creates a JAX-RS "Bad Request" response including a map of all violation
	 * fields, and their message. This can then be used by clients to show
	 * violations.
	 * 
	 * @param violations
	 *            A set of violations that needs to be reported
	 * @return JAX-RS response containing all violations
	 */
	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
		log.fine("Validation completed. violations found: " + violations.size());

		Map<String, String> responseObj = new HashMap<>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}

}
