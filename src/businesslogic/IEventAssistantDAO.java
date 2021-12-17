package businesslogic;

import domain.EventAssistant;
import domain.EventConstancy;
import domain.Member;

import java.util.List;

public interface IEventAssistantDAO {

    public List<EventConstancy> displayAllEventConstanciesByIdAcademicEvent(String idAcademicEvent) throws BusinessLogicException;
    public List<String> foundRegulatoryNotesByIdEventConstancy(String idEventConstancy) throws BusinessLogicException;
    public List<Member> foundValidatorsByIdEventConstancy(String idEventConstancy) throws BusinessLogicException;
    public EventConstancy foundEventConstancyByIdEventConstancy(String idEventConstancy) throws BusinessLogicException;
    public boolean addEventConstancy(EventConstancy eventConstancy) throws BusinessLogicException;
    public int getLastIdNumber(String column, String table) throws BusinessLogicException;
    public boolean addRegulatoryNoteToEventConstancy(String idEventConstancy, String regulatoryNote) throws BusinessLogicException;
    public boolean addValidatorToEventConstancy(String idEventConstancy, String curpMember) throws BusinessLogicException;
    public boolean addEventAssistant(EventAssistant eventAssistant) throws BusinessLogicException;
    public EventAssistant foundEventAssistantByEmail(String email) throws BusinessLogicException;
    public boolean generatePDFEventConstancy(EventConstancy eventConstancy, int numerOfConstancyDownload) throws BusinessLogicException;

}
