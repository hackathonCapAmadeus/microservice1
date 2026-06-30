package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/calculator")
public class CalculatorResource {

    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public CalculationResult add(@QueryParam("a") double a, @QueryParam("b") double b) {
        return new CalculationResult(a + b, "add");
    }

    @GET
    @Path("/subtract")
    @Produces(MediaType.APPLICATION_JSON)
    public CalculationResult subtract(@QueryParam("a") double a, @QueryParam("b") double b) {
        return new CalculationResult(a - b, "subtract");
    }

    @GET
    @Path("/multiply")
    @Produces(MediaType.APPLICATION_JSON)
    public CalculationResult multiply(@QueryParam("a") double a, @QueryParam("b") double b) {
        return new CalculationResult(a * b, "multiply");
    }

    @GET
    @Path("/divide")
    @Produces(MediaType.APPLICATION_JSON)
    public CalculationResult divide(@QueryParam("a") double a, @QueryParam("b") double b) {
        if (b == 0) {
            return new CalculationResult(0, "divide", "Error: Division by zero");
        }
        return new CalculationResult(a / b, "divide");
    }

    public static class CalculationResult {
        public double result;
        public String operation;
        public String error;

        public CalculationResult(double result, String operation) {
            this.result = result;
            this.operation = operation;
            this.error = null;
        }

        public CalculationResult(double result, String operation, String error) {
            this.result = result;
            this.operation = operation;
            this.error = error;
        }
    }
}