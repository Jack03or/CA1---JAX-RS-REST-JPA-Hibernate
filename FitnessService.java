package com.FitnessCentre.FitnessCentreRest;

import java.util.List;

//import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.FitnessCentre.FitnessCentreRest.dao.MemberDao;
import com.FitnessCentre.FitnessCentreRest.dao.MembershipPlanDao;
import com.FitnessCentre.FitnessCentreRest.dao.PaymentDAO;
import com.FitnessCentre.FitnessCentreRest.model.Member;
import com.FitnessCentre.FitnessCentreRest.model.MembershipPlan;
import com.FitnessCentre.FitnessCentreRest.model.Payment;

@Path("/fitness")
public class FitnessService {
	
	// Member Crud Operations 
	
	@GET
	@Path("/test")
	@Produces("text/plain")
	public String testEndpoint() {
	    return "FitnessService is working!";
	}
	
	@POST
	@Path("/posttest")
	@Produces("text/plain")
	public String postTest() {
	    return "POST is working buddy";
	}


    // Add a member to DB
    @POST
    @Path("/newMember")
    @Produces("text/plain")
    public String addMemberToDBJSON(Member member) {
        MemberDao dao = new MemberDao();
        dao.persist(member);
        return "Member " + member.getName() + " added successfully!";
    }

    // Update member
    @PUT
    @Path("/updateMember")
    @Produces("application/json")
    public Member updateMember(Member member) {
        MemberDao dao = new MemberDao();
        return dao.merge(member);
    }

    //Delete member
    @DELETE
    @Path("/member/{id}")
    public String deleteMember(@PathParam("id") int id) {
        MemberDao mDao = new MemberDao();
        mDao.removeMember(id);
        return "Member with ID " + id + " deleted successfully.";
    }


    //Get all members
    @GET
    @Path("/allmembersfromdb")
    @Produces({"application/json", "application/xml"})
    public List<Member> getAllMembersFromDB() {
        MemberDao dao = new MemberDao();
        return dao.getAllMembers();
    }
    
    
    /// Membership Plan Crud Operations ///
    
 // Add new membership plan
    @POST
    @Path("/newMembershipPlan")
    @Produces("text/plain")
    public String addMembershipPlan(MembershipPlan plan) {
        MembershipPlanDao dao = new MembershipPlanDao();
        dao.persist(plan);
        return "Membership Plan added: " + plan.getDescription();
    }

    // Update membership plan
    @PUT
    @Path("/updateMembershipPlan")
    @Produces("application/json")
    public MembershipPlan updateMembershipPlan(MembershipPlan plan) {
        MembershipPlanDao dao = new MembershipPlanDao();
        return dao.merge(plan);
    }

    //Delete membeship plan
    @DELETE
    @Path("/membershipPlan/{id}")
    @Produces("text/plain")
    public String deleteMembershipPlan(@PathParam("id") int id) {
        MembershipPlanDao dao = new MembershipPlanDao();
        dao.removeMembershipPlan(id);
        return "Membership Plan with ID " + id + " deleted successfully.";
    }


    //Get all membership plans
    @GET
    @Path("/allMembershipPlans") // this works 
    @Produces({"application/json","application/xml"})
    public List<MembershipPlan> getAllMembershipPlans() {
        MembershipPlanDao dao = new MembershipPlanDao();
        return dao.getAll();
    }

    
    /// Payment CRUD Operations ///
    
    //Add new payment
    @POST
    @Path("/newPayment")
    @Produces("text/plain")
    public String addPayment(Payment payment) {
        PaymentDAO dao = new PaymentDAO();
        dao.persist(payment);
        return "Payment added: " + payment.getAmount();
    }
    
    //Delete a payment
    @DELETE
    @Path("/deletePayment/{id}")
    public String deletePayment(@PathParam("id") int id) {
        PaymentDAO dao = new PaymentDAO();
        dao.removePayment(id);
        return "Payment with ID " + id + " deleted successfully.";
    }

    
    //Get all XML Format
    @GET
    @Path("/allPayments")
    @Produces({"application/xml", "application/json"})
    public List<Payment> getAllPayments() {
        PaymentDAO dao = new PaymentDAO();
        return dao.getAllPayments();
    }


}
