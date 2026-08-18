<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="Trip Management - TFMS" scope="request"/>
<jsp:include page="common/header.jsp" />
<jsp:include page="common/navigation.jsp" />

<div class="content-wrapper">
    <div class="container">
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2><i class="fas fa-route"></i> Trip Management</h2>
                    <p class="mb-0">Schedule and manage all trips</p>
                </div>
                <button class="btn btn-light btn-lg" data-bs-toggle="modal" data-bs-target="#addTripModal">
                    <i class="fas fa-plus-circle"></i> Schedule New Trip
                </button>
            </div>
        </div>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show">
                <i class="fas fa-check-circle"></i> ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show">
                <i class="fas fa-exclamation-circle"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <div class="card">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0"><i class="fas fa-list"></i> All Trips (${trips.size()})</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty trips}">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle"></i> No trips found.
                    </div>
                </c:if>
                
                <c:if test="${not empty trips}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Vehicle</th>
                                    <th>Driver</th>
                                    <th>Route</th>
                                    <th>Start Time</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="trip" items="${trips}">
                                    <tr>
                                        <td>${trip.tripId}</td>
                                        <td><strong>${trip.vehicle.registrationNumber}</strong></td>
                                        <td>Driver #${trip.driverId}</td>
                                        <td>
                                            <small>
                                                <i class="fas fa-map-marker-alt text-success"></i> ${trip.startLocation}<br>
                                                <i class="fas fa-map-marker-alt text-danger"></i> ${trip.endLocation}
                                            </small>
                                        </td>
                                        <td>${trip.startTimeFormatted}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${trip.endTime == null}">
                                                    <span class="badge bg-primary">
                                                        <i class="fas fa-spinner"></i> Ongoing
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-success">
                                                        <i class="fas fa-check"></i> Completed
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="btn-group">
                                                <button class="btn btn-sm btn-info" 
                                                        onclick="viewTrip(${trip.tripId}, '${trip.vehicle.registrationNumber}', ${trip.driverId}, '${trip.startLocation}', '${trip.endLocation}', '${trip.startTimeFormatted}', '${trip.endTimeFormatted}')">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <c:if test="${trip.endTime == null}">
                                                    <a href="${pageContext.request.contextPath}/trips/complete/${trip.tripId}" 
                                                       class="btn btn-sm btn-success">
                                                        <i class="fas fa-check"></i>
                                                    </a>
                                                </c:if>
                                                <a href="${pageContext.request.contextPath}/trips/delete/${trip.tripId}" 
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Delete this trip?')">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Add Trip Modal -->
<div class="modal fade" id="addTripModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title"><i class="fas fa-plus-circle"></i> Schedule New Trip</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/trips/add" method="post">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-car"></i> Vehicle *</label>
                        <select name="vehicleId" class="form-select" required>
                            <option value="">-- Select Vehicle --</option>
                            <c:forEach var="vehicle" items="${vehicles}">
                                <option value="${vehicle.vehicleId}">
                                    ${vehicle.registrationNumber} (${vehicle.capacity} tons)
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-user"></i> Driver ID *</label>
                        <input type="number" name="driverId" class="form-control" 
                               placeholder="Enter driver ID" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-map-marker-alt"></i> Start Location *</label>
                        <input type="text" name="startLocation" class="form-control" 
                               placeholder="e.g., Chennai" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-map-marker-alt"></i> End Location *</label>
                        <input type="text" name="endLocation" class="form-control" 
                               placeholder="e.g., Bangalore" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-clock"></i> Start Time *</label>
                        <input type="datetime-local" name="startTime" class="form-control" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-success">
                        <i class="fas fa-save"></i> Schedule Trip
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View Trip Modal -->
<div class="modal fade" id="viewTripModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title"><i class="fas fa-eye"></i> Trip Details</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <table class="table table-borderless">
                    <tr>
                        <th width="40%"><i class="fas fa-hashtag"></i> Trip ID:</th>
                        <td id="view_tripId"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-car"></i> Vehicle:</th>
                        <td id="view_vehicle"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-user"></i> Driver ID:</th>
                        <td id="view_driverId"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-map-marker-alt"></i> Start Location:</th>
                        <td id="view_startLocation"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-map-marker-alt"></i> End Location:</th>
                        <td id="view_endLocation"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-clock"></i> Start Time:</th>
                        <td id="view_startTime"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-clock"></i> End Time:</th>
                        <td id="view_endTime"></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script>
function viewTrip(id, vehicle, driverId, startLoc, endLoc, startTime, endTime) {
    document.getElementById('view_tripId').textContent = id;
    document.getElementById('view_vehicle').textContent = vehicle;
    document.getElementById('view_driverId').textContent = 'Driver #' + driverId;
    document.getElementById('view_startLocation').textContent = startLoc;
    document.getElementById('view_endLocation').textContent = endLoc;
    document.getElementById('view_startTime').textContent = startTime;
    document.getElementById('view_endTime').textContent = endTime || 'Ongoing';
    
    new bootstrap.Modal(document.getElementById('viewTripModal')).show();
}
</script>

<jsp:include page="common/footer.jsp" />