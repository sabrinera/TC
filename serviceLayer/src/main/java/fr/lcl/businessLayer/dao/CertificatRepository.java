package fr.lcl.businessLayer.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import fr.lcl.businessLayer.model.Certificat;

public interface CertificatRepository extends JpaRepository<Certificat, String>{
	

}
