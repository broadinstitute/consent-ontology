/**
 * Copyright 2014 Genome Bridge LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.broadinstitute.dsde.consent.ontology.datause.models;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Some extends UseRestriction {
    private String property;
    private UseRestriction object;

    public Some() {}

    public Some(String prop, UseRestriction obj) {
        this.property = prop;
        this.object = obj;
    }

    public String getProperty() { return property; }
    public UseRestriction getTarget() { return object; }

    public void setProperty(String p) { property = p; }
    public void setTarget(UseRestriction r) { object = r; }

    public String toString() {
        return String.format("{ \"type\": \"some\", \"property\": \"%s\", \"target\": %s }",
                property, object.toString());
    }

    public int hashCode() {
        return 37 * property.hashCode() + object.hashCode();
    }

    public boolean equals(Object o) {
        if(!(o instanceof Some)) return false;
        Some r = (Some)o;
        return property.equals(r.property) && object.equals(r.object);
    }

    @Override
    public OntClass createOntologicalRestriction(OntModel model) {
        Property prop = model.createProperty(property);
        Resource objectClass = object.createOntologicalRestriction(model);
        return model.createSomeValuesFromRestriction(null, prop, objectClass);
    }

    public boolean visitAndContinue(UseRestrictionVisitor visitor) {
        return object.visit(visitor);
    }

}