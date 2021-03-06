package org.broadinstitute.dsp.ontology.factories

import org.broadinstitute.dsp.ontology.models.JsonProtocols
import spray.json._
import DefaultJsonProtocol._
import org.broadinstitute.dsp.ontology.models.DataUseModels._

object DataUseFactory {
    object Objects {
        val noGeneralUse: DataUse = dataUseBuilder(generalUse = Some(false))
        val generalUse: DataUse = dataUseBuilder(generalUse = Some(true))
        val hmbResearch: DataUse = dataUseBuilder(hmbResearch = Some(true))
        val notGeneralHmb: DataUse = dataUseBuilder(generalUse = Some(false), hmbResearch = Some(true))
        val manualReview: DataUse = dataUseBuilder(manualReview = Some(true))
    }

    def getDataUseString(dataUse: DataUse): String = {
        implicit val dataUseFormat: JsonProtocols.DataUseFormat.type = JsonProtocols.DataUseFormat
        dataUse.toJson.compactPrint
    }

    val generalUse: String = getDataUseString(Objects.generalUse)

    val hmbResearch: String = getDataUseString(Objects.hmbResearch)

    val notGeneralHmb: String = getDataUseString(Objects.notGeneralHmb)

    val manualReview: String = getDataUseString(Objects.manualReview)
}
