package com.FitnessCentre.FitnessCentreRest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.FitnessCentre.FitnessCentreRest.dao.MemberDao;
import com.FitnessCentre.FitnessCentreRest.model.Member;


@Path("/fitness")
public class FitnessService {
	
	
    //Add member
	@POST
	@Path("/newMember")
	@Consumes("application/json")
	@Produces("text/plain")
	public String addMemberToDBJSON(Member member) {
	    MemberDao dao = new MemberDao();
	    dao.persist(member);
	    return "Member " + member.getName() + " added successfully!";
	}
	
	//Update Member
	@PUT
    @Path("/updateMember/")
    @Produces("application/json")
    public Member updateMember(Member member){
		MemberDao dao = new MemberDao();
		return dao.merge(member);	
    }
	
	//Delete Member
	@DELETE
	@Path("/deleteMember/{name}")
	@Produces("text/plain")
	public String deleteMember(@PathParam("name") String name) {
	    MemberDao dao = new MemberDao();
	    Member m = dao.getMemberByName(name);
	    dao.removeMember(m);
	    return "Member " + m.getName() + " deleted";
	}
	
	//Find All Members
	@GET
    @Path("/allmembersfromdb")
    @Produces("application/xml")
    public List<Member> getAllMembersFromDB(){
        MemberDao dao = new MemberDao();
        return dao.getAllMembers();
    }

	
	
}
