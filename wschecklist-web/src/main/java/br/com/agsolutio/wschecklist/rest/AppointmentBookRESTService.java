
package br.com.agsolutio.wschecklist.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.agsolutio.wschecklist.data.AppointmentBookRepository;
import br.com.agsolutio.wschecklist.model.AppointmentBook;
import br.com.agsolutio.wschecklist.model.Consultant;
import br.com.agsolutio.wschecklist.model.Sector;
import br.com.agsolutio.wschecklist.service.AppointmentBookRegistration;

/**
 * This class produces a RESTful service to read/write the contents of the appointmentBook table.
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 * 
 * @since 15/06/2017
 */
@Path("/appointmentBook")
@RequestScoped
public class AppointmentBookRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private AppointmentBookRepository repository;

    @Inject
    AppointmentBookRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppointmentBook> listAllAppointments() {
        return repository.findAllAppointments();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public AppointmentBook lookupAppointmentById(@PathParam("id") long id) {
        AppointmentBook appointment = repository.findByAppontamentAsConsultant(id);
        if (appointment == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return appointment;
    }
    
	@GET
	@Path("/del/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteAppointmentBookById(@PathParam("id") long id) {
		AppointmentBook ask = repository.findById(id);
		if (ask == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		registration.deleteById(id);
	}


    /**
     * Creates a new {@link AppointmentBook} from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAppointmentBook(AppointmentBook appointmentBook) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            validateAppointment(appointmentBook);

            registration.register(appointmentBook);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("Consultant", "Consultant taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * <p>
     * Validates the given Member variable and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.
     * </p>
     * <p>
     * If the error is caused because an existing member with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.
     * </p>
     * 
     * @param appontmentBook {@link Consultant} to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If COnsultant with the same sector already exists
     */
    private void validateAppointment(AppointmentBook appontmentBook) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<AppointmentBook>> violations = validator.validate(appontmentBook);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the consultant
        if (consultantlAlreadyExists(appontmentBook.getCosnsultant())) {
            throw new ValidationException("Unique Consultant Violation");
        }
        
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     * 
     * @param violations A set of violations that needs to be reported
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

    /**
     * Checks if a consultant with the same sector is already registered. 
     * 
     * @param sector The {@link Sector} to check
     * @return True if the sector already exists, and false otherwise
     */
    public boolean consultantlAlreadyExists(Consultant consultant) {
        AppointmentBook appointmentBook = null;
        try {
            appointmentBook = repository.findByConsultantAsMatricula(consultant.getMatricula());
        } catch (NoResultException e) {
            // ignore
        }
        return appointmentBook != null;
    }
    
}
