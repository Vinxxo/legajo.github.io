package proyecto_legajo.legajo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto_legajo.legajo.Entity.EstadoIntercambio;
import proyecto_legajo.legajo.Entity.EstadoReporte;
import proyecto_legajo.legajo.Repository.IntercambiosRepository;
import proyecto_legajo.legajo.Repository.reportesUsuarioRepository;
import proyecto_legajo.legajo.Repository.usuarioRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin("*")
public class AdminDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);

    @Autowired
    private usuarioRepository usuarioRepo;

    @Autowired
    private reportesUsuarioRepository reportesRepo;

    @Autowired
    private IntercambiosRepository intercambiosRepo;

    // Obtener datos para la gráfica de usuarios reportados este mes
    @GetMapping("/reported-users")
    public ResponseEntity<?> getReportedUsersData() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            
            // Obtener todos los reportes del mes actual
            List<proyecto_legajo.legajo.Entity.reportesUsuario> allReports = reportesRepo.findAll().stream()
                .filter(r -> r.getFechaReporte() != null && r.getFechaReporte().isAfter(startOfMonth))
                .toList();

            // Agrupar por día del mes
            Map<Integer, Integer> reportsByDay = new HashMap<>();
            for (int i = 1; i <= 30; i++) {
                reportsByDay.put(i, 0);
            }

            int accumulated = 0;
            for (proyecto_legajo.legajo.Entity.reportesUsuario report : allReports) {
                int day = report.getFechaReporte().getDayOfMonth();
                accumulated++;
                if (day <= 30) {
                    reportsByDay.put(day, accumulated);
                }
            }

            // Construir response
            Map<String, Object> response = new HashMap<>();
            response.put("labels", List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
                                          "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                                          "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"));
            
            List<Integer> data = List.of(
                reportsByDay.getOrDefault(1, 0), reportsByDay.getOrDefault(2, 0),
                reportsByDay.getOrDefault(3, 0), reportsByDay.getOrDefault(4, 0),
                reportsByDay.getOrDefault(5, 0), reportsByDay.getOrDefault(6, 0),
                reportsByDay.getOrDefault(7, 0), reportsByDay.getOrDefault(8, 0),
                reportsByDay.getOrDefault(9, 0), reportsByDay.getOrDefault(10, 0),
                reportsByDay.getOrDefault(11, 0), reportsByDay.getOrDefault(12, 0),
                reportsByDay.getOrDefault(13, 0), reportsByDay.getOrDefault(14, 0),
                reportsByDay.getOrDefault(15, 0), reportsByDay.getOrDefault(16, 0),
                reportsByDay.getOrDefault(17, 0), reportsByDay.getOrDefault(18, 0),
                reportsByDay.getOrDefault(19, 0), reportsByDay.getOrDefault(20, 0),
                reportsByDay.getOrDefault(21, 0), reportsByDay.getOrDefault(22, 0),
                reportsByDay.getOrDefault(23, 0), reportsByDay.getOrDefault(24, 0),
                reportsByDay.getOrDefault(25, 0), reportsByDay.getOrDefault(26, 0),
                reportsByDay.getOrDefault(27, 0), reportsByDay.getOrDefault(28, 0),
                reportsByDay.getOrDefault(29, 0), reportsByDay.getOrDefault(30, 0)
            );
            
            response.put("data", data);
            logger.info("✓ Datos de usuarios reportados obtenidos: total={}", allReports.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error al obtener datos de usuarios reportados", e);
            return ResponseEntity.status(500).body(Map.of("error", "Error al obtener datos"));
        }
    }

    // Obtener datos para la gráfica de intercambios completados este mes
    @GetMapping("/completed-exchanges")
    public ResponseEntity<?> getCompletedExchangesData() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            
            // Obtener todos los intercambios completados del mes actual
            List<proyecto_legajo.legajo.Entity.intercambios> completedExchanges = intercambiosRepo.findAll().stream()
                .filter(e -> e.getFechaCompletado() != null && 
                           e.getFechaCompletado().isAfter(startOfMonth) &&
                           e.getEstadoInter() == EstadoIntercambio.completado)
                .toList();

            // Agrupar por día del mes
            Map<Integer, Integer> exchangesByDay = new HashMap<>();
            for (int i = 1; i <= 30; i++) {
                exchangesByDay.put(i, 0);
            }

            int accumulated = 0;
            for (proyecto_legajo.legajo.Entity.intercambios exchange : completedExchanges) {
                int day = exchange.getFechaCompletado().getDayOfMonth();
                accumulated++;
                if (day <= 30) {
                    exchangesByDay.put(day, accumulated);
                }
            }

            // Construir response
            Map<String, Object> response = new HashMap<>();
            response.put("labels", List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
                                          "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                                          "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"));
            
            List<Integer> data = List.of(
                exchangesByDay.getOrDefault(1, 0), exchangesByDay.getOrDefault(2, 0),
                exchangesByDay.getOrDefault(3, 0), exchangesByDay.getOrDefault(4, 0),
                exchangesByDay.getOrDefault(5, 0), exchangesByDay.getOrDefault(6, 0),
                exchangesByDay.getOrDefault(7, 0), exchangesByDay.getOrDefault(8, 0),
                exchangesByDay.getOrDefault(9, 0), exchangesByDay.getOrDefault(10, 0),
                exchangesByDay.getOrDefault(11, 0), exchangesByDay.getOrDefault(12, 0),
                exchangesByDay.getOrDefault(13, 0), exchangesByDay.getOrDefault(14, 0),
                exchangesByDay.getOrDefault(15, 0), exchangesByDay.getOrDefault(16, 0),
                exchangesByDay.getOrDefault(17, 0), exchangesByDay.getOrDefault(18, 0),
                exchangesByDay.getOrDefault(19, 0), exchangesByDay.getOrDefault(20, 0),
                exchangesByDay.getOrDefault(21, 0), exchangesByDay.getOrDefault(22, 0),
                exchangesByDay.getOrDefault(23, 0), exchangesByDay.getOrDefault(24, 0),
                exchangesByDay.getOrDefault(25, 0), exchangesByDay.getOrDefault(26, 0),
                exchangesByDay.getOrDefault(27, 0), exchangesByDay.getOrDefault(28, 0),
                exchangesByDay.getOrDefault(29, 0), exchangesByDay.getOrDefault(30, 0)
            );
            
            response.put("data", data);
            logger.info("✓ Datos de intercambios completados obtenidos: total={}", completedExchanges.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error al obtener datos de intercambios completados", e);
            return ResponseEntity.status(500).body(Map.of("error", "Error al obtener datos"));
        }
    }

    // Obtener estadísticas generales
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        try {
            long totalUsers = usuarioRepo.count();
            long totalReports = reportesRepo.count();
            
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            
            long monthlyReports = reportesRepo.findAll().stream()
                .filter(r -> r.getFechaReporte() != null && r.getFechaReporte().isAfter(startOfMonth))
                .count();
            
            long monthlyExchanges = intercambiosRepo.findAll().stream()
                .filter(e -> e.getFechaCompletado() != null && 
                           e.getFechaCompletado().isAfter(startOfMonth) &&
                           e.getEstadoInter() == EstadoIntercambio.completado)
                .count();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", totalUsers);
            stats.put("totalReports", totalReports);
            stats.put("monthlyReports", monthlyReports);
            stats.put("monthlyExchanges", monthlyExchanges);
            
            logger.info("✓ Estadísticas del dashboard obtenidas: usuarios={}, reportes={}", totalUsers, totalReports);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("✗ Error al obtener estadísticas", e);
            return ResponseEntity.status(500).body(Map.of("error", "Error al obtener estadísticas"));
        }
    }
}
