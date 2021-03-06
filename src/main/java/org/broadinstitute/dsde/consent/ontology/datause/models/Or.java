package org.broadinstitute.dsde.consent.ontology.datause.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import org.broadinstitute.dsde.consent.ontology.datause.models.visitor.UseRestrictionVisitor;

import java.util.Arrays;

public class Or extends UseRestriction {

    private String type = "or";

    private UseRestriction[] operands;

    public Or() {
    }

    public Or(UseRestriction... operands) {
        this.operands = operands;
        if (operands.length < 2) {
            throw new IllegalArgumentException("Disjunction must have at least two operands");
        }
    }

    @JsonIgnore
    public String getType() {
        return type;
    }

    public void setOperands(UseRestriction[] ops) {
        this.operands = ops.clone();
    }

    public UseRestriction[] getOperands() {
        return operands;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, operands);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Or &&
                Arrays.deepEquals(this.operands, ((Or) o).operands);
    }

    @Override
    public OntClass createOntologicalRestriction(OntModel model) {
        RDFNode[] nodes = new RDFNode[operands.length];
        for(int i = 0; i < nodes.length; i++) {
            nodes[i] = operands[i].createOntologicalRestriction(model);
        }
        RDFList list = model.createList(nodes);
        return model.createUnionClass(null, list);
    }

    public boolean visitAndContinue(UseRestrictionVisitor visitor) {
        for (UseRestriction child : operands) {
            if (!child.visit(visitor)) {
                return false;
            }
        }
        return true;
    }

}
