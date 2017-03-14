package componentesExternos;
import java.util.List;
import tpAnual_dominio.CentroGestionParticipacion;
import tpAnual_dominio.Banco;

public interface AdaptadorSistemaExterno {
	public	List<CentroGestionParticipacion> obtenerCGPs(String lugar);
	public	List<Banco> obtenerBancos(String nombreBanco,String servicio);
}
