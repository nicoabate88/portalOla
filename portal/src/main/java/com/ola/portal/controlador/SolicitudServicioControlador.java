
package com.ola.portal.controlador;


import com.ola.portal.entidad.Admin;
import com.ola.portal.excepcion.MiException;
import com.ola.portal.servicio.ClienteServicio;
import com.ola.portal.servicio.SolicitudServicioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/solicitud")
public class SolicitudServicioControlador {
    
    @Autowired
    private SolicitudServicioServicio solicitudServicio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping("/registrar")
    public String registrar(HttpSession session, ModelMap modelo){
        
        Admin logueado = (Admin) session.getAttribute("usuariosession");
        
        modelo.addAttribute("usuario", logueado);
        modelo.put("clientes", clienteServicio.buscarClientes());
        
        return "solicitudRegistrar.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String tipoServicio, @RequestParam String observacion, @RequestParam Long idCliente, @RequestParam Long idUsuario, ModelMap modelo){
        
        try {
            
            solicitudServicio.crearSolicitud(tipoServicio, observacion, idCliente, idUsuario);
            
            modelo.put("exito", "Solicitud de Servicio registrada exitosamente");
            return "index.html";
            
        } catch (MiException ex) {
        
            modelo.put("error", ex.getMessage());
            
            return "solicitudRegistrar.html";
        }
    }
}
