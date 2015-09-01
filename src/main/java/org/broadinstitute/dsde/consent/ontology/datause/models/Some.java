package org.broadinstitute.dsde.consent.ontology.datause.models;

import com.google.common.base.Objects;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Some extends UseRestriction {

    private String type = "some";

    private String property;

    private UseRestriction target;

    public Some() {
    }

    public Some(String prop, UseRestriction obj) {
        this.property = prop;
        this.target = obj;
    }

    public String getType() {
        return type;
    }

    public void setProperty(String p) {
        property = p;
    }

    public String getProperty() {
        return property;
    }

    public UseRestriction getTarget() {
        return target;
    }

    public void setTarget(UseRestriction r) {
        target = r;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, target);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Some &&
                Objects.equal(this.property, ((Some) o).property) &&
                Objects.equal(this.target, ((Some) o).target);
    }

    @Override
    public OntClass createOntologicalRestriction(OntModel model) {
        Property prop = model.createProperty(property);
        Resource objectClass = target.createOntologicalRestriction(model);
        return model.createSomeValuesFromRestriction(null, prop, objectClass);
    }

    public boolean visitAndContinue(UseRestrictionVisitor visitor) {
        return target.visit(visitor);
    }

}