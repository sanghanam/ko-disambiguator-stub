package edu.kaist.tgm.ko.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

@Path("/ko/tgm/stub/service")
public class KOTGMService {

	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response getPost(String input) throws JSONException, IllegalStateException  {

		JSONObject error = new JSONObject();
		String result;

		Validator v = new Validator();
		boolean isValidInput = v.isValidTemplatorInJSON(input);

		String sampleTemplatorResult = null;
		
		if (isValidInput) {
			// Call disambiguation module...

			// Get output of Disambiguator
			sampleTemplatorResult = "{ \"question\": \"어떤 강이 서울을 흐르는가?\","
					+ " \"query\": \"SELECT ?v2 WHERE  { ?v2 ?v6 ?v1 . ?v2 ?v3 ?v5 . }\","
					+ " \"slots\": [ {\"s\": \"v5\", \"p\": \"is\", \"o\": \"owl:NamedIndividual\"},"
					+ " {\"s\": \"v5\", \"p\": \"verbalization\", \"o\": \"서울\"},"
					+ " {\"s\": \"v6\", \"p\": \"is\", \"o\": \"<http://lodqa.org/vocabulary/sort_of>\"},"
					+ " {\"s\": \"v1\", \"p\": \"is\", \"o\": \"owl:Class\"}, "
					+ " {\"s\": \"v1\", \"p\": \"verbalization\", \"o\": \"강\"},"
					+ " {\"s\": \"v3\", \"p\": \"is\", \"o\": \"owl:Property\"}, "
					+ " {\"s\": \"v3\", \"p\": \"verbalization\", \"o\": \"흐르는가\"} ],"
					+ " \"score\": \"1.0\" }";
			
			// Check output validation
			boolean isValidOutput = v.isValidTemplatorOutJSON(sampleTemplatorResult);

			// Output of Templator is valid form.
			if (isValidOutput) {
				result = sampleTemplatorResult;
			}
			// Output of Templator is invalid form.
			else {
				error.put("error", "output");
				JSONArray errorCause = new JSONArray();
				for (int i = 0; i < v.etOut.size(); i++) {
					errorCause.put(v.etOut.get(i).getType());
				}
				error.put("error_cause", errorCause);
				result = error.toJSONString();
			}
			
		}
		// Input of Templator is invalid form.
		else {
			error.put("error", "input");
			JSONArray errorCause = new JSONArray();
			for (int i = 0; i < v.etIn.size(); i++) {
				errorCause.put(v.etIn.get(i).getType());
			}
			error.put("error_cause", errorCause);
			result = error.toJSONString();
		}
		
		return Response.ok(result).entity(result)
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept").build();

	}

	@OPTIONS
	@Consumes("application/x-www-form-urlencoded; charset=utf-8")
	@Produces("text/plain; charset=utf-8")
	public Response getOptions(String input) throws JSONException {

		return Response.ok().header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept").build();

	}

}
