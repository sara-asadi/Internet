<%@page import="app.repository.FlightRepository"%>

<jsp:include page="header.jsp" />

<%
	String destination = request.getParameter("destination");
%>

<div class="container">
	<form action="bookFlight" method="get">
		<div class="form-group col-md-8">
			<label for="exampleFormControlInput1">Destination</label> <input
				type="text" readonly class="form-control" name="destination"
				value="<%=destination%>">
		</div>
		<div class="form-group col-md-4">
			<label for="exampleFormControlInput1">Available Seats</label> <input
				type="text" readonly class="form-control" id="availableSeats"
				value="<%=FlightRepository.getInstance().getAvailableSeats(destination)%>">
		</div>
		<div class="form-row">
			<% 
				String classValue = "form-group col-md-6 ";
				if(request.getAttribute("badFirstName") != null) {
					classValue += "has-error";
				}
			%>
			<div class="<%=classValue%>">
				<label class="control-label" for="inputEmail4">First Name</label> <input type="text"
					class="form-control" name="firstName" id="inputEmail4" placeholder="First Name">
					<%if(request.getParameter("badFirstName") != null) {%>	
		        	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
		        	<%}%>
			</div>
			<div class="form-group col-md-6 <%if(request.getAttribute("badLastName") != null) {%>has-error <%}%>">
				<label class="control-label" for="inputEmail4">Last Name</label> <input type="text"
					class="form-control" name="lastName" id="inputEmail4" placeholder="Last Name">
					<%if(request.getParameter("badLastName") != null) {%>	
		        	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
		        	<%}%>
			</div>
		</div>
		<div class="form-group col-md-2">
			<label class="control-label" for="exampleFormControlSelect1">Number Of Tickets</label> <select
				class="form-control" name="numberOfTickets">
				<option>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5</option>
			</select>
		</div>
		<div class="col-md-12 text-center">
			<button type="submit" class="btn btn-primary">Book</button>
		</div>
	</form>
</div>
<jsp:include page="footer.jsp" />
