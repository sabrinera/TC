package fr.lcl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import fr.lcl.businessLayer.model.Certificat;

public class CertificatItemProcessor implements ItemProcessor<Certificat, Certificat> {

    private static final Logger log = LoggerFactory.getLogger(CertificatItemProcessor.class);

    @Override
    public Certificat process(final Certificat Certificat) throws Exception {
    	log.info("processing Certicat object");
        return new Certificat();
    }
    
}