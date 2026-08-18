<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="Maintenance Management - TFMS" scope="request"/>
<jsp:include page="common/header.jsp" />
<jsp:include page="common/navigation.jsp" />

<div class="content-wrapper">
    <div class="container">
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2><i class="fas fa-tools"></i> Maintenance Management</h2>
                    <p class="mb-0">Schedule and track vehicle maintenance</p>
                </div>
                <button class="btn btn-light btn-lg" data-bs-toggle="modal" data-bs-target="#addMaintenanceModal">
                    <i class="fas fa-plus-circle"></i> Schedule Maintenance
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
            <div class="card-header bg-warning text-dark">
                <h5 class="mb-0"><i class="fas fa-list"></i> Maintenance Records (${maintenanceRecords.size()})</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty maintenanceRecords}">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle"></i> No maintenance records found.
                    </div>
                </c:if>
                
                <c:if test="${not empty maintenanceRecords}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Vehicle</th>
                                    <th>Description</th>
                                    <th>Scheduled Date</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="maintenance" items="${maintenanceRecords}">
                                    <tr>
                                        <td>${maintenance.maintenanceId}</td>
                                        <td><strong>${maintenance.vehicle.registrationNumber}</strong></td>
                                        <td>${maintenance.description}</td>
                                        <td>
                                            ${maintenance.scheduledDateFormatted}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${maintenance.status == 'SCHEDULED'}">
                                                    <span class="badge bg-warning">
                                                        <i class="fas fa-clock"></i> ${maintenance.status}
                                                    </span>
                                                </c:when>
                                                <c:when test="${maintenance.status == 'COMPLETED'}">
                                                    <span class="badge bg-success">
                                                        <i class="fas fa-check"></i> ${maintenance.status}
                                                    </span>
                                                </c:when>
                                                <c:when test="${maintenance.status == 'IN_PROGRESS'}">
                                                    <span class="badge bg-primary">
                                                        <i class="fas fa-spinner"></i> ${maintenance.status}
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${maintenance.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="btn-group">
                                                <button class="btn btn-sm btn-info" 
                                                        onclick="viewMaintenance(${maintenance.maintenanceId}, '${maintenance.vehicle.registrationNumber}', '${maintenance.description}', '${maintenance.scheduledDateFormatted}', '${maintenance.status}')">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <button class="btn btn-sm btn-warning" 
                                                        onclick="editMaintenance(${maintenance.maintenanceId}, ${maintenance.vehicle.vehicleId}, '${maintenance.description}', '${maintenance.status}')">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <a href="${pageContext.request.contextPath}/maintenance/delete/${maintenance.maintenanceId}" 
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Delete this maintenance record?')">
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

<!-- Add Maintenance Modal -->
<div class="modal fade" id="addMaintenanceModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-warning text-dark">
                <h5 class="modal-title"><i class="fas fa-plus-circle"></i> Schedule Maintenance</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/maintenance/schedule" method="post">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-car"></i> Vehicle *</label>
                        <select name="vehicleId" class="form-select" required>
                            <option value="">-- Select Vehicle --</option>
                            <c:forEach var="vehicle" items="${vehicles}">
                                <option value="${vehicle.vehicleId}">
                                    ${vehicle.registrationNumber}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-file-alt"></i> Description *</label>
                        <textarea name="description" class="form-control" rows="3" 
                                  placeholder="e.g., Oil change and tire rotation" required></textarea>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-calendar"></i> Scheduled Date *</label>
                        <input type="datetime-local" name="scheduledDate" class="form-control" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-info-circle"></i> Status *</label>
                        <select name="status" class="form-select" required>
                            <option value="">-- Select Status --</option>
                            <option value="SCHEDULED" selected>Scheduled</option>
                            <option value="IN_PROGRESS">In Progress</option>
                            <option value="COMPLETED">Completed</option>
                            <option value="CANCELLED">Cancelled</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-warning">
                        <i class="fas fa-save"></i> Schedule
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Maintenance Modal -->
<div class="modal fade" id="editMaintenanceModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title"><i class="fas fa-edit"></i> Edit Maintenance</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form id="editMaintenanceForm" method="post">
                <div class="modal-body">
                    <input type="hidden" id="edit_maintenanceId" name="maintenanceId">
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-car"></i> Vehicle *</label>
                        <select id="edit_vehicleId" name="vehicleId" class="form-select" required>
                            <c:forEach var="vehicle" items="${vehicles}">
                                <option value="${vehicle.vehicleId}">
                                    ${vehicle.registrationNumber}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-file-alt"></i> Description *</label>
                        <textarea id="edit_description" name="description" class="form-control" rows="3" required></textarea>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-calendar"></i> Scheduled Date *</label>
                        <input type="datetime-local" id="edit_scheduledDate" name="scheduledDate" class="form-control" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-info-circle"></i> Status *</label>
                        <select id="edit_status" name="status" class="form-select" required>
                            <option value="SCHEDULED">Scheduled</option>
                            <option value="IN_PROGRESS">In Progress</option>
                            <option value="COMPLETED">Completed</option>
                            <option value="CANCELLED">Cancelled</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Update
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View Maintenance Modal -->
<div class="modal fade" id="viewMaintenanceModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title"><i class="fas fa-eye"></i> Maintenance Details</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <table class="table table-borderless">
                    <tr>
                        <th width="40%"><i class="fas fa-hashtag"></i> Maintenance ID:</th>
                        <td id="view_maintenanceId"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-car"></i> Vehicle:</th>
                        <td id="view_vehicle"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-file-alt"></i> Description:</th>
                        <td id="view_description"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-calendar"></i> Scheduled Date:</th>
                        <td id="view_scheduledDate"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-info-circle"></i> Status:</th>
                        <td id="view_status"></td>
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
function editMaintenance(id, vehicleId, description, status) {
    document.getElementById('edit_maintenanceId').value = id;
    document.getElementById('edit_vehicleId').value = vehicleId;
    document.getElementById('edit_description').value = description;
    document.getElementById('edit_status').value = status;
    document.getElementById('editMaintenanceForm').action = '${pageContext.request.contextPath}/maintenance/edit/' + id;
    new bootstrap.Modal(document.getElementById('editMaintenanceModal')).show();
}

function viewMaintenance(id, vehicle, description, scheduledDate, status) {
    document.getElementById('view_maintenanceId').textContent = id;
    document.getElementById('view_vehicle').textContent = vehicle;
    document.getElementById('view_description').textContent = description;
    document.getElementById('view_scheduledDate').textContent = scheduledDate;
    
    let statusBadge = '';
    if(status === 'SCHEDULED') {
        statusBadge = '<span class="badge bg-warning"><i class="fas fa-clock"></i> ' + status + '</span>';
    } else if(status === 'COMPLETED') {
        statusBadge = '<span class="badge bg-success"><i class="fas fa-check"></i> ' + status + '</span>';
    } else if(status === 'IN_PROGRESS') {
        statusBadge = '<span class="badge bg-primary"><i class="fas fa-spinner"></i> ' + status + '</span>';
    } else {
        statusBadge = '<span class="badge bg-secondary">' + status + '</span>';
    }
    document.getElementById('view_status').innerHTML = statusBadge;
    
    new bootstrap.Modal(document.getElementById('viewMaintenanceModal')).show();
}
</script>

<jsp:include page="common/footer.jsp" />