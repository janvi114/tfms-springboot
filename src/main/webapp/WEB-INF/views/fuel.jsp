<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="Fuel Management - TFMS" scope="request"/>
<jsp:include page="common/header.jsp" />
<jsp:include page="common/navigation.jsp" />

<div class="content-wrapper">
    <div class="container">
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2><i class="fas fa-gas-pump"></i> Fuel Management</h2>
                    <p class="mb-0">Track and manage fuel consumption</p>
                </div>
                <button class="btn btn-light btn-lg" data-bs-toggle="modal" data-bs-target="#addFuelModal">
                    <i class="fas fa-plus-circle"></i> Add Fuel Record
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
            <div class="card-header bg-danger text-white">
                <h5 class="mb-0"><i class="fas fa-list"></i> Fuel Records (${fuelRecords.size()})</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty fuelRecords}">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle"></i> No fuel records found.
                    </div>
                </c:if>
                
                <c:if test="${not empty fuelRecords}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Vehicle</th>
                                    <th>Date</th>
                                    <th>Quantity (L)</th>
                                    <th>Cost (₹)</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="fuel" items="${fuelRecords}">
                                    <tr>
                                        <td>${fuel.fuelId}</td>
                                        <td><strong>${fuel.vehicle.registrationNumber}</strong></td>
                                        <td>${fuel.dateFormatted}</td>
                                        <td>${fuel.fuelQuantity} L</td>
                                        <td>₹${fuel.cost}</td>
                                        <td>
                                            <div class="btn-group">
                                                <button class="btn btn-sm btn-info" 
                                                        onclick="viewFuel(${fuel.fuelId}, '${fuel.vehicle.registrationNumber}', '${fuel.dateFormatted}', '${fuel.fuelQuantity}', '${fuel.cost}')">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <a href="${pageContext.request.contextPath}/fuel/delete/${fuel.fuelId}" 
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Delete this fuel record?')">
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

<!-- Add Fuel Modal -->
<div class="modal fade" id="addFuelModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title"><i class="fas fa-plus-circle"></i> Add Fuel Record</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/fuel/add" method="post">
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
                        <label class="form-label"><i class="fas fa-fill-drip"></i> Fuel Quantity (Liters) *</label>
                        <input type="number" step="0.01" name="fuelQuantity" class="form-control" 
                               placeholder="e.g., 50.5" required>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-rupee-sign"></i> Cost (₹) *</label>
                        <input type="number" step="0.01" name="cost" class="form-control" 
                               placeholder="e.g., 5000.00" required>
                    </div>
                    
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle"></i> Date will be automatically set to current date/time
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-danger">
                        <i class="fas fa-save"></i> Save Record
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View Fuel Modal -->
<div class="modal fade" id="viewFuelModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title"><i class="fas fa-eye"></i> Fuel Record Details</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <table class="table table-borderless">
                    <tr>
                        <th width="40%"><i class="fas fa-hashtag"></i> Fuel Record ID:</th>
                        <td id="view_fuelId"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-car"></i> Vehicle:</th>
                        <td id="view_vehicle"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-calendar"></i> Date:</th>
                        <td id="view_date"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-fill-drip"></i> Fuel Quantity:</th>
                        <td id="view_quantity"></td>
                    </tr>
                    <tr>
                        <th><i class="fas fa-rupee-sign"></i> Cost:</th>
                        <td id="view_cost"></td>
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
function viewFuel(id, vehicle, date, quantity, cost) {
    document.getElementById('view_fuelId').textContent = id;
    document.getElementById('view_vehicle').textContent = vehicle;
    document.getElementById('view_date').textContent = date;
    document.getElementById('view_quantity').textContent = quantity + ' L';
    document.getElementById('view_cost').textContent = '₹' + cost;
    
    new bootstrap.Modal(document.getElementById('viewFuelModal')).show();
}
</script>

<jsp:include page="common/footer.jsp" />