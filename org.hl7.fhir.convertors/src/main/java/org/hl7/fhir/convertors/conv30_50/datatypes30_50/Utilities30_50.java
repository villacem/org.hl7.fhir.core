package org.hl7.fhir.convertors.conv30_50.datatypes30_50;

import org.hl7.fhir.convertors.VersionConvertorConstants;
import org.hl7.fhir.r5.model.CodeType;
import org.hl7.fhir.r5.model.Extension;
import org.hl7.fhir.r5.model.Enumerations.FHIRTypes;
import org.hl7.fhir.utilities.Utilities;

public class Utilities30_50 {

  public static void convertType(org.hl7.fhir.dstu3.model.CodeType src, org.hl7.fhir.r5.model.Enumeration<FHIRTypes> tgt) {
    if (Utilities.existsInList(src.primitiveValue(), "Media")) {
      setType(tgt, src.primitiveValue(), "DocumentReference");
    
    } else if (Utilities.existsInList(src.primitiveValue(), "DeviceUseStatement")) {
      setType(tgt, src.primitiveValue(), "DeviceUsage");
    
    } else if (Utilities.existsInList(src.primitiveValue(), "DocumentManifest")) {
      setType(tgt, src.primitiveValue(), "List");
    
    } else if (Utilities.existsInList(src.primitiveValue(), "MedicinalProductAuthorization")) {
      setType(tgt, src.primitiveValue(), "RegulatedAuthorization");
    
    } else if (Utilities.existsInList(src.primitiveValue(), "RequestGroup")) {
      setType(tgt, src.primitiveValue(), "RequestOrchestration");
      
    } else if (Utilities.existsInList(src.primitiveValue(), "DeviceComponent")) {
        setType(tgt, src.primitiveValue(), "Device");      
    
    } else {
      tgt.setValue(org.hl7.fhir.r5.model.Enumerations.FHIRTypes.fromCode(src.primitiveValue()));
    }
  }

  private static void setType(org.hl7.fhir.r5.model.Enumeration<FHIRTypes> tgt, String original, String value) {
    tgt.setValueAsString(value);
    tgt.addExtension(new Extension().setUrl(VersionConvertorConstants.EXT_OPDEF_ORIGINAL_TYPE).setValue(new CodeType(original))); 
  }

  public static void convertType(org.hl7.fhir.r5.model.Enumeration<FHIRTypes> src, org.hl7.fhir.dstu3.model.CodeType tgt) {
    if (src.hasExtension(VersionConvertorConstants.EXT_OPDEF_ORIGINAL_TYPE)) {
      tgt.setValueAsString(src.getExtensionString(VersionConvertorConstants.EXT_OPDEF_ORIGINAL_TYPE));
    } else {
      tgt.setValue(src.asStringValue());
    }
    
  }

}
