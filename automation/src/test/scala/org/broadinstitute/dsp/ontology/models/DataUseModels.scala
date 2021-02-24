package org.broadinstitute.dsp.ontology.models

object DataUseModels {
    case class DataUse(
        generalUse: Option[Boolean] = None,
        hmbResearch: Option[Boolean] = None,
        diseaseRestrictions: Option[Seq[String]] = None,
        populationOriginsAncestry: Option[Boolean] = None,
        populationStructure: Option[Boolean] = None,
        commercialUse: Option[Boolean] = None,
        methodsResearch: Option[Boolean] = None,
        aggregateResearch: Option[String] = None,
        controlSetOption: Option[String] = None,
        gender: Option[String] = None,
        pediatric: Option[Boolean] = None,
        populationRestrictions: Option[Seq[String]] = None,
        dateRestriction: Option[String] = None,
        recontactingDataSubjects: Option[Boolean] = None,
        recontactMay: Option[String] = None,
        recontactMust: Option[String] = None,
        genomicPhenotypicData: Option[String] = None,
        otherRestrictions: Option[Boolean] = None,
        cloudStorage: Option[String] = None,
        ethicsApprovalRequired: Option[Boolean] = None,
        collaboratorRequired: Option[Boolean] = None,
        geographicalRestrictions: Option[String] = None,
        other: Option[String] = None,
        secondaryOther: Option[String] = None,
        illegalBehavior: Option[Boolean] = None,
        addiction: Option[Boolean] = None,
        sexualDiseases: Option[Boolean] = None,
        stigmatizeDiseases: Option[Boolean] = None,
        vulnerablePopulations: Option[Boolean] = None,
        psychologicalTraits: Option[Boolean] = None,
        nonBiomedical: Option[Boolean] = None,
        manualReview: Option[Boolean] = None,
        geneticStudiesOnly: Option[Boolean] = None,
        publicationResults: Option[Boolean] = None,
        genomicResults: Option[Boolean] = None,
        genomicSummaryResults: Option[String] = None,
        collaborationInvestigators: Option[Boolean] = None,
        publicationMoratorium: Option[String] = None
    )

    def dataUseBuilder(
        generalUse: Option[Boolean] = None,
        hmbResearch: Option[Boolean] = None,
        diseaseRestrictions: Option[Seq[String]] = None,
        populationOriginsAncestry: Option[Boolean] = None,
        populationStructure: Option[Boolean] = None,
        commercialUse: Option[Boolean] = None,
        methodsResearch: Option[Boolean] = None,
        aggregateResearch: Option[String] = None,
        controlSetOption: Option[String] = None,
        gender: Option[String] = None,
        pediatric: Option[Boolean] = None,
        populationRestrictions: Option[Seq[String]] = None,
        dateRestriction: Option[String] = None,
        recontactingDataSubjects: Option[Boolean] = None,
        recontactMay: Option[String] = None,
        recontactMust: Option[String] = None,
        genomicPhenotypicData: Option[String] = None,
        otherRestrictions: Option[Boolean] = None,
        cloudStorage: Option[String] = None,
        ethicsApprovalRequired: Option[Boolean] = None,
        collaboratorRequired: Option[Boolean] = None,
        geographicalRestrictions: Option[String] = None,
        other: Option[String] = None,
        secondaryOther: Option[String] = None,
        illegalBehavior: Option[Boolean] = None,
        addiction: Option[Boolean] = None,
        sexualDiseases: Option[Boolean] = None,
        stigmatizeDiseases: Option[Boolean] = None,
        vulnerablePopulations: Option[Boolean] = None,
        psychologicalTraits: Option[Boolean] = None,
        nonBiomedical: Option[Boolean] = None,
        manualReview: Option[Boolean] = None,
        geneticStudiesOnly: Option[Boolean] = None,
        publicationResults: Option[Boolean] = None,
        genomicResults: Option[Boolean] = None,
        genomicSummaryResults: Option[String] = None,
        collaborationInvestigators: Option[Boolean] = None,
        publicationMoratorium: Option[String] = None): DataUse = {
            DataUse(
                generalUse,
                hmbResearch,
                diseaseRestrictions,
                populationOriginsAncestry,
                populationStructure,
                commercialUse,
                methodsResearch,
                aggregateResearch,
                controlSetOption,
                gender,
                pediatric,
                populationRestrictions,
                dateRestriction,
                recontactingDataSubjects,
                recontactMay,
                recontactMust,
                genomicPhenotypicData,
                otherRestrictions,
                cloudStorage,
                ethicsApprovalRequired,
                collaboratorRequired,
                geographicalRestrictions,
                other,
                secondaryOther,
                illegalBehavior,
                addiction,
                sexualDiseases,
                stigmatizeDiseases,
                vulnerablePopulations,
                psychologicalTraits,
                nonBiomedical,
                manualReview,
                geneticStudiesOnly,
                publicationResults,
                genomicResults,
                genomicSummaryResults,
                collaborationInvestigators,
                publicationMoratorium
            )
    }
}