package by.gsu.epamlab.interfaces;

import java.util.List;

import by.gsu.epamlab.beans.Play;
import by.gsu.epamlab.exceptions.DAOException;

public interface IPlayDAO {
     public List<Play> getPlaysList() throws DAOException;
     public Play getPlay(int dateID) throws DAOException;
     public void addPlays(List<Play> plays) throws DAOException;
     
     public void update() throws DAOException;
}
