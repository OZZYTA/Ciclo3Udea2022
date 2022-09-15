package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.repo.MovimientosRepository;
import com.UdeA.Ciclo3.service.EmpleadoService;
import com.UdeA.Ciclo3.service.EmpresaService;
import com.UdeA.Ciclo3.service.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ControllerFull {
    @Autowired
    EmpresaService empresaService;
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    MovimientosService movimientosService;

    @Autowired
    MovimientosRepository movimientosRepositor;

    //EMPRESAS
    @GetMapping ({"/","/VerEmpresas"})
    public String viewEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> listaEmpresas=empresaService.getAllEmpresas();
        model.addAttribute("emplist",listaEmpresas);
        model.addAttribute("mensaje",mensaje);
        return "verEmpresas"; //Llamamos al HTML
    }

    @GetMapping("/AgregarEmpresa")
    public String nuevaEmpresa(Model model, @ModelAttribute("mensaje") String mensaje){
        Empresa emp= new Empresa();
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje",mensaje);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaService.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpresa";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empresa emp=empresaService.getEmpresaById(id);
        //Creamos un atributo para el modelo, que se llame igualmente emp y es el que ira al html para llenar o alimentar campos
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }


    @PostMapping("/ActualizarEmpresa")
    public String updateEmpresa(@ModelAttribute("emp") Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaService.saveOrUpdateEmpresa(emp)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpresa/"+emp.getId();

    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empresaService.deleteEmpresa(id)==true){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerEmpresas";
    }



    //EMPLEADOS
    @GetMapping ("/VerEmpleados")
    public String viewEmpleados(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empleado> listaEmpleados=empleadoService.getAllEmpleado();
        model.addAttribute("emplelist",listaEmpleados);
        model.addAttribute("mensaje",mensaje);
        return "verEmpleados"; //Llamamos al HTML
    }

    @GetMapping("/AgregarEmpleado")
    public String nuevoEmpleado(Model model, @ModelAttribute("mensaje") String mensaje){
        Empleado empl= new Empleado();
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje",mensaje);
        List<Empresa> listaEmpresas= empresaService.getAllEmpresas();
        model.addAttribute("emprelist",listaEmpresas);
        return "agregarEmpleado"; //Llamar HTML
    }

    @PostMapping("/GuardarEmpleado")
    public String guardarEmpleado(Empleado empl, RedirectAttributes redirectAttributes){
        String passEncriptada=passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);
        if(empleadoService.saveOrUpdateEmpleado(empl)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpleado";
    }

    @GetMapping("/EditarEmpleado/{id}")
    public String editarEmpleado(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empleado empl=empleadoService.getEmpleadoById(id).get();
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas= empresaService.getAllEmpresas();
        model.addAttribute("emprelist",listaEmpresas);
        return "editarEmpleado";
    }

    @PostMapping("/ActualizarEmpleado")
    public String updateEmpleado(@ModelAttribute("empl") Empleado empl, RedirectAttributes redirectAttributes){
        Integer id=empl.getId(); //Sacamos el id del objeto empl
        String Oldpass=empleadoService.getEmpleadoById(id).get().getPassword(); //Con ese id consultamos la contraseña que ya esta en la base
        if(!empl.getPassword().equals(Oldpass)){
            String passEncriptada=passwordEncoder().encode(empl.getPassword());
            empl.setPassword(passEncriptada);
        }
        if(empleadoService.saveOrUpdateEmpleado(empl)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpleado/"+empl.getId();

    }

    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empleadoService.deleteEmpleado(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerEmpleados";
    }

    @GetMapping("/Empresa/{id}/Empleados") //Filtrar los empleados por empresa
    public String verEmpleadosPorEmpresa(@PathVariable("id") Integer id, Model model){
        List<Empleado> listaEmpleados = empleadoService.obtenerPorEmpresa(id);
        model.addAttribute("emplelist",listaEmpleados);
        return "verEmpleados"; //Llamamos al html con el emplelist de los empleados filtrados
    }


    //MOVIMIENTOS

    @RequestMapping ("/VerMovimientos")// Controlador que nos lleva al template donde veremos todos los movimientos
    public String viewMovimientos(@RequestParam(value="pagina", required=false, defaultValue = "1") int NumeroPagina,
                                  @RequestParam(value="medida", required=false, defaultValue = "5") int medida,
                                  Model model, @ModelAttribute("mensaje") String mensaje){
        Page<MovimientoDinero> paginaMovimientos= movimientosRepositor.findAll(PageRequest.of(NumeroPagina,medida));
        model.addAttribute("movlist",paginaMovimientos.getContent());
        model.addAttribute("paginas",new int[paginaMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual", NumeroPagina);
        model.addAttribute("mensaje",mensaje);
        Long sumaMonto=movimientosService.obtenerSumaMontos();
        model.addAttribute("SumaMontos",sumaMonto);//Mandamos la suma de todos los montos a la plantilla
        return "verMovimientos"; //Llamamos al HTML
    }

    @GetMapping("/AgregarMovimiento") //Controlador que nos lleva al template donde podremos crear un nuevo movimiento
    public String nuevoMovimiento(Model model, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero movimiento= new MovimientoDinero();
        model.addAttribute("mov",movimiento);
        model.addAttribute("mensaje",mensaje);
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String correo=auth.getName();
        Integer idEmpleado=movimientosService.IdPorCorreo(correo);
        model.addAttribute("idEmpleado",idEmpleado);
        return "agregarMovimiento"; //Llamar HTML
    }

    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosService.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarMovimiento";
    }

    @GetMapping("/EditarMovimiento/{id}")
    public String editarMovimento(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero mov=movimientosService.getMovimientoById(id);
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("mov",mov);
        model.addAttribute("mensaje", mensaje);
        List<Empleado> listaEmpleados= empleadoService.getAllEmpleado();
        model.addAttribute("emplelist",listaEmpleados);
        return "editarMovimiento";
    }

    @PostMapping("/ActualizarMovimiento")
    public String updateMovimiento(@ModelAttribute("mov") MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosService.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarMovimiemto/"+mov.getId();

    }

    @GetMapping("/EliminarMovimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (movimientosService.deleteMovimiento(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerMovimientos";
    }

    @GetMapping("/Empleado/{id}/Movimientos") //Filtro de movimientos por empleados
    public String movimientosPorEmpleado(@PathVariable("id")Integer id, Model model){
        List<MovimientoDinero> movlist = movimientosService.obtenerPorEmpleado(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto=movimientosService.MontosPorEmpleado(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "verMovimientos"; //Llamamos al HTML
    }

    @GetMapping("/Empresa/{id}/Movimientos") //Filtro de movimientos por empresa
    public String movimientosPorEmpresa(@PathVariable("id")Integer id, Model model){
        List<MovimientoDinero> movlist = movimientosService.obtenerPorEmpresa(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto=movimientosService.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "verMovimientos"; //Llamamos al HTML
    }

    //Controlador que me lleva al template de No autorizado
    @RequestMapping(value="/Denegado")
    public String accesoDenegado(){
        return "accessDenied";
    }


    //Metodo para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
