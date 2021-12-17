package businesslogic;

import domain.Prerequirement;

import java.util.List;

public interface IPrerequirementDAO {
    public boolean addPrerequirement(Prerequirement prerequirement);
    public List<Prerequirement> displayAllPrerequirements();
    public Prerequirement findPrerequirementById(String idPreriquirementFind);
    public boolean updatePrerequirement(Prerequirement prerequirement);
    public boolean deletePrerequirementById(String idPreriquirement);

}
