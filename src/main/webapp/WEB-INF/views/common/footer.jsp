<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <!-- Footer -->
    <footer class="bg-dark text-white text-center py-4 mt-5">
        <div class="container">
            <p class="mb-0">
                <i class="fas fa-truck-moving"></i> 
                Transportation Fleet Management System &copy; 2024
            </p>
            <p class="mb-0 mt-2">
                <small>Powered by Spring Boot & MySQL</small>
            </p>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- jQuery (optional, for additional functionality) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Custom JS -->
    <script>
        // Auto-hide alerts after 5 seconds
        setTimeout(function() {
            var alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                var bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
        
        // Confirm delete actions
        function confirmDelete(message) {
            return confirm(message || 'Are you sure you want to delete this item?');
        }
        
        // Add active class to current nav item
        document.addEventListener('DOMContentLoaded', function() {
            var currentPath = window.location.pathname;
            var navLinks = document.querySelectorAll('.nav-link');
            
            navLinks.forEach(function(link) {
                if (link.getAttribute('href') === currentPath) {
                    link.classList.add('active');
                }
            });
        });
    </script>
</body>
</html>