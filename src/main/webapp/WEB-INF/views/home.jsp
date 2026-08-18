<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="Home - TFMS" scope="request"/>
<jsp:include page="common/header.jsp" />
<jsp:include page="common/navigation.jsp" />

<div class="content-wrapper">
    <div class="container">
        <!-- Welcome Section -->
        <div class="page-header text-center">
            <h1><i class="fas fa-truck-moving"></i> Transportation Fleet Management System</h1>
            <p class="lead mb-0">Manage your fleet efficiently with real-time tracking and analytics</p>
        </div>
        
        <!-- Dashboard Statistics -->
        <div class="row mb-4">
            <div class="col-md-3 mb-3">
                <div class="stat-card">
                    <i class="fas fa-car text-primary"></i>
                    <h3 class="text-primary">${dashboardData.totalVehicles}</h3>
                    <p>Total Vehicles</p>
                    <a href="${pageContext.request.contextPath}/vehicles" class="btn btn-sm btn-outline-primary">
                        View All
                    </a>
                </div>
            </div>
            
            <div class="col-md-3 mb-3">
                <div class="stat-card">
                    <i class="fas fa-route text-success"></i>
                    <h3 class="text-success">${dashboardData.totalTrips}</h3>
                    <p>Total Trips</p>
                    <a href="${pageContext.request.contextPath}/trips" class="btn btn-sm btn-outline-success">
                        View All
                    </a>
                </div>
            </div>
            
            <div class="col-md-3 mb-3">
                <div class="stat-card">
                    <i class="fas fa-tools text-warning"></i>
                    <h3 class="text-warning">${dashboardData.scheduledMaintenance}</h3>
                    <p>Scheduled Maintenance</p>
                    <a href="${pageContext.request.contextPath}/maintenance" class="btn btn-sm btn-outline-warning">
                        View All
                    </a>
                </div>
            </div>
            
            <div class="col-md-3 mb-3">
                <div class="stat-card">
                    <i class="fas fa-gas-pump text-danger"></i>
                    <h3 class="text-danger">${dashboardData.totalFuelRecords}</h3>
                    <p>Fuel Records</p>
                    <a href="${pageContext.request.contextPath}/fuel" class="btn btn-sm btn-outline-danger">
                        View All
                    </a>
                </div>
            </div>
        </div>
        

        <!-- Quick Actions section removed for simplicity -->

        <!-- Key Highlights -->
        <div class="row mt-4">
            <div class="col-md-6 mb-3">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h5 class="mb-0"><i class="fas fa-check-circle"></i> Active Status</h5>
                    </div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Active Vehicles
                                <span class="badge bg-success">${dashboardData.activeVehicles}</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Ongoing Trips
                                <span class="badge bg-primary">${dashboardData.ongoingTrips}</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Completed Trips
                                <span class="badge bg-info">${dashboardData.completedTrips}</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-3">
                <div class="card">
                    <div class="card-header bg-warning text-dark">
                        <h5 class="mb-0"><i class="fas fa-exclamation-triangle"></i> Attention Required</h5>
                    </div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Inactive Vehicles
                                <span class="badge bg-secondary">${dashboardData.inactiveVehicles}</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Overdue Maintenance
                                <span class="badge bg-danger">${dashboardData.overdueMaintenance}</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Completed Maintenance
                                <span class="badge bg-success">${dashboardData.completedMaintenance}</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />