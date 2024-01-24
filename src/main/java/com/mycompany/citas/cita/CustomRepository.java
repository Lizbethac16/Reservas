 package com.mycompany.citas.Cita;

import java.util.List;

public interface CustomRepository{
    public List<Cita> findAllByEmail(String email);
}