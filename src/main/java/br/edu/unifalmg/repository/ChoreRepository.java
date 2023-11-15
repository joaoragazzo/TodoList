package br.edu.unifalmg.repository;

import br.edu.unifalmg.domain.Chore;

import java.util.List;

public interface ChoreRepository {

    public List<Chore> load();
    public boolean saveAll(List<Chore> chores);

    public boolean save(Chore chore);
    public boolean update(Chore chore);


}
