<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="Vehicle Management - TFMS" scope="request"/>
<jsp:include page="common/header.jsp" />
<jsp:include page="common/navigation.jsp" />

<div class="content-wrapper">
    <div class="container">
        <!-- Page Header -->
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2><i class="fas fa-car"></i> Vehicle Management</h2>
                    <p class="mb-0">Complete vehicle operations in one place</p>
                </div>
                <button class="btn btn-light btn-lg" data-bs-toggle="modal" data-bs-target="#addVehicleModal">
                    <i class="fas fa-plus-circle"></i> Add New Vehicle
                </button>
            </div>
        </div>
        
        <!-- Alert Messages -->
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
        
        <!-- Vehicles Table -->
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><i class="fas fa-list"></i> All Vehicles (${vehicles.size()})</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty vehicles}">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle"></i> No vehicles found. Click "Add New Vehicle" to get started.
                    </div>
                </c:if>
                
                <c:if test="${not empty vehicles}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Registration</th>
                                    <th>Capacity</th>
                                    <th>Status</th>
                                    <th>Last Serviced</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="vehicle" items="${vehicles}">
                                    <tr>
                                        <td>${vehicle.vehicleId}</td>
                                        <td><strong>${vehicle.registrationNumber}</strong></td>
                                        <td>${vehicle.capacity} tons</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${vehicle.status == 'ACTIVE'}">
                                                    <span class="badge bg-success">
                                                        <i class="fas fa-check"></i> ${vehicle.status}
                                                    </span>
                                                </c:when>
                                                <c:when test="${vehicle.status == 'MAINTENANCE'}">
                                                    <span class="badge bg-warning">
                                                        <i class="fas fa-tools"></i> ${vehicle.status}
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">
                                                        <i class="fas fa-ban"></i> ${vehicle.status}
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${vehicle.lastServicedDateFormatted}</td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <button class="btn btn-sm btn-info" 
                                                        onclick="viewVehicle(${vehicle.vehicleId}, '${vehicle.registrationNumber}', ${vehicle.capacity}, '${vehicle.status}', '${vehicle.lastServicedDateFormatted}')">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <button class="btn btn-sm btn-warning" 
                                                        onclick="editVehicle(${vehicle.vehicleId}, '${vehicle.registrationNumber}', ${vehicle.capacity}, '${vehicle.status}')">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <a href="${pageContext.request.contextPath}/vehicles/delete/${vehicle.vehicleId}" 
                                                   class="btn btn-sm btn-danger" 
                                                   onclick="return confirm('Delete this vehicle?')">
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

<!-- Add Vehicle Modal -->
<div class="modal fade" id="addVehicleModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title"><i class="fas fa-plus-circle"></i> Add New Vehicle</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/vehicles/add" method="post">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-id-card"></i> Registration Number *</label>
                        <input type="text" name="registrationNumber" class="form-control" 
                               placeholder="e.g., TN-01-AB-1234" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-weight-hanging"></i> Capacity (tons) *</label>
                        <input type="number" name="capacity" class="form-control" 
                               placeholder="e.g., 10" min="1" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-info-circle"></i> Status *</label>
                        <select name="status" class="form-select" required>
                            <option value="">-- Select Status --</option>
                            <option value="ACTIVE">Active</option>
                            <option value="INACTIVE">Inactive</option>
                            <option value="MAINTENANCE">Under Maintenance</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Save Vehicle
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Vehicle Modal -->
<div class="modal fade" id="editVehicleModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-warning text-dark">
                <h5 class="modal-title"><i class="fas fa-edit"></i> Edit Vehicle</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form id="editVehicleForm" method="post">
                <div class="modal-body">
                    <input type="hidden" id="edit_vehicleId" name="vehicleId">
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-id-card"></i> Registration Number *</label>
                        <input type="text" id="edit_registrationNumber" name="registrationNumber" 
                               class="form-control" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-weight-hanging"></i> Capacity (tons) *</label>
                        <input type="number" id="edit_capacity" name="capacity" 
                               class="form-control" min="1" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-info-circle"></i> Status *</label>
                        <select id="edit_status" name="status" class="form-select" required>
                            <option value="ACTIVE">Active</option>
                            <option value="INACTIVE">Inactive</option>
                            <option value="MAINTENANCE">Under Maintenance</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-warning">
                        <i class="fas fa-save"></i> Update Vehicle
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View Vehicle Modal -->
<div class="modal fade" id="viewVehicleModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title"><i class="fas fa-eye"></i> Vehicle Details</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <table class="table table-borderless">
                    <tr>
                        <th width="40%"><i class="fas fa-hashtag"></i> Vehicle ID:</th>
                        <td id="view_vehicleId"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-id-card"></i> Registration Number:</th>
                        <td id="view_registrationNumber"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-weight-hanging"></i> Capacity:</th>
                        <td id="view_capacity"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-info-circle"></i> Status:</th>
                        <td id="view_status"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-calendar"></i> Last Serviced:</th>
                        <td id="view_lastServicedDate"></td>
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
function editVehicle(id, regNo, capacity, status) {
    document.getElementById('edit_vehicleId').value = id;
    document.getElementById('edit_registrationNumber').value = regNo;
    document.getElementById('edit_capacity').value = capacity;
    document.getElementById('edit_status').value = status;
    document.getElementById('editVehicleForm').action = '${pageContext.request.contextPath}/vehicles/edit/' + id;
    new bootstrap.Modal(document.getElementById('editVehicleModal')).show();
}

function viewVehicle(id, regNo, capacity, status, lastServiced) {
    document.getElementById('view_vehicleId').textContent = id;
    document.getElementById('view_registrationNumber').textContent = regNo;
    document.getElementById('view_capacity').textContent = capacity + ' tons';
    
    let statusBadge = '';
    if(status === 'ACTIVE') {
        statusBadge = '<span class="badge bg-success"><i class="fas fa-check"></i> ' + status + '</span>';
    } else if(status === 'MAINTENANCE') {
        statusBadge = '<span class="badge bg-warning"><i class="fas fa-tools"></i> ' + status + '</span>';
    } else {
        statusBadge = '<span class="badge bg-secondary"><i class="fas fa-ban"></i> ' + status + '</span>';
    }
    document.getElementById('view_status').innerHTML = statusBadge;
    document.getElementById('view_lastServicedDate').textContent = lastServiced;
    
    new bootstrap.Modal(document.getElementById('viewVehicleModal')).show();
}
</script>

<jsp:include page="common/footer.jsp" />