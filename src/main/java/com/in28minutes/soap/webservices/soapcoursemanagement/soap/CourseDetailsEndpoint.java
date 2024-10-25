package com.in28minutes.soap.webservices.soapcoursemanagement.soap;

import com.in28minutes.courses.*;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.enums.ErrorsAndExceptions;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.enums.Status;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.in28minutes.soap.webservices.soapcoursemanagement.soap.bean.Course;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.service.CourseDetailsService;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    CourseDetailsService service;

    // method
    // input - GetCourseDetailsRequest
    // output - GetCourseDetailsResponse

    // http://in28minutes.com/courses
    // GetCourseDetailsRequest
    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processGetCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();

        Course course = service.findById(request.getId());

        if(course == null){
            throw new CourseNotFoundException(ErrorsAndExceptions.COURSE_NOT_FOUND.getMessage());
        }
        response.setCourseDetails(mapCourse(course));
        return response;
    }


    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processGetAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        List<Course> courses = service.findAll();
        for (Course course : courses) {
            response.getCourseDetails().add(mapCourse(course));
        }
        return response;
    }


    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse processDeleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();

        Status status = service.deleteById(request.getId());
        response.setStatus(mapStatus(status));
        return response;
    }

    private com.in28minutes.courses.Status mapStatus(Status status) {
        return (status == Status.SUCCESS) ? com.in28minutes.courses.Status.SUCCESS : com.in28minutes.courses.Status.FAILURE;
    }


    private CourseDetails mapCourse(Course course) {

        CourseDetails courseDetails = new CourseDetails();

        courseDetails.setId(course.getId());

        courseDetails.setName(course.getName());

        courseDetails.setDescription(course.getDescription());

        return courseDetails;
    }


}
