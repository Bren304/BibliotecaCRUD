package crudtrabajosdegrado;

public abstract class TrabajoDeGrado {
    
    int tipo, estado, cedulaEstudiante;
    String nombreEstudiante, nombreDocente, nombreCodirector, fechaPropuesta, correoInstitucional;
    
    abstract void Registrar();
    
    abstract void Modificar();
    
    abstract void Eliminar();
    
    abstract void Devolver();
    
    abstract void SubirArchivo();
}
