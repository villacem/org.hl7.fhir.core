package org.hl7.fhir.r4.model.codesystems;

/*
  Copyright (c) 2011+, HL7, Inc.
  All rights reserved.
  
  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:
  
   * Redistributions of source code must retain the above copyright notice, this 
     list of conditions and the following disclaimer.
   * Redistributions in binary form must reproduce the above copyright notice, 
     this list of conditions and the following disclaimer in the documentation 
     and/or other materials provided with the distribution.
   * Neither the name of HL7 nor the names of its contributors may be used to 
     endorse or promote products derived from this software without specific 
     prior written permission.
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
  POSSIBILITY OF SUCH DAMAGE.
  
*/

// Generated on Wed, Jan 30, 2019 16:19-0500 for FHIR v4.0.0

import org.hl7.fhir.exceptions.FHIRException;

public enum V3ActUncertainty {

  /**
   * Specifies that the act statement is made without explicit tagging of
   * uncertainty. This is the normal statement, meaning that it is not free of
   * errors and uncertainty may still exist.
   */
  N,
  /**
   * Specifies that the originator of the Act statement does not have full
   * confidence in the applicability (i.e., in event mood: factual truth) of the
   * statement.
   */
  U,
  /**
   * added to help the parsers
   */
  NULL;

  public static V3ActUncertainty fromCode(String codeString) throws FHIRException {
    if (codeString == null || "".equals(codeString))
      return null;
    if ("N".equals(codeString))
      return N;
    if ("U".equals(codeString))
      return U;
    throw new FHIRException("Unknown V3ActUncertainty code '" + codeString + "'");
  }

  public String toCode() {
    switch (this) {
    case N:
      return "N";
    case U:
      return "U";
    case NULL:
      return null;
    default:
      return "?";
    }
  }

  public String getSystem() {
    return "http://terminology.hl7.org/CodeSystem/v3-ActUncertainty";
  }

  public String getDefinition() {
    switch (this) {
    case N:
      return "Specifies that the act statement is made without explicit tagging of uncertainty. This is the normal statement, meaning that it is not free of errors and uncertainty may still exist.";
    case U:
      return "Specifies that the originator of the Act statement does not have full confidence in the applicability (i.e., in event mood: factual truth) of the statement.";
    case NULL:
      return null;
    default:
      return "?";
    }
  }

  public String getDisplay() {
    switch (this) {
    case N:
      return "stated with no assertion of uncertainty";
    case U:
      return "stated with uncertainty";
    case NULL:
      return null;
    default:
      return "?";
    }
  }

}