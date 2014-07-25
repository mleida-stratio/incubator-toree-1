package com.ibm.kernel.protocol.v5.content

import org.scalatest.{FunSpec, Matchers}
import play.api.data.validation.ValidationError
import play.api.libs.json._

import com.ibm.kernel.protocol.v5._

class HistoryReplySpec extends FunSpec with Matchers {
  val historyReplyJson: JsValue = Json.parse("""
  {
    "history": ["<STRING>", "<STRING2>", "<STRING3>"]
  }
  """)

  val historyReply = HistoryReply(
    List("<STRING>", "<STRING2>", "<STRING3>")
  )

  describe("HistoryReply") {
    describe("implicit conversions") {
      it("should implicitly convert from valid json to a HistoryReply instance") {
        // This is the least safe way to convert as an error is thrown if it fails
        historyReplyJson.as[HistoryReply] should be (historyReply)
      }

      it("should also work with asOpt") {
        // This is safer, but we lose the error information as it returns
        // None if the conversion fails
        val newCompleteRequest = historyReplyJson.asOpt[HistoryReply]

        newCompleteRequest.get should be (historyReply)
      }

      it("should also work with validate") {
        // This is the safest as it collects all error information (not just first error) and reports it
        val CompleteRequestResults = historyReplyJson.validate[HistoryReply]

        CompleteRequestResults.fold(
          (invalid: Seq[(JsPath, Seq[ValidationError])]) => println("Failed!"),
          (valid: HistoryReply) => valid
        ) should be (historyReply)
      }

      it("should implicitly convert from a HistoryReply instance to valid json") {
        Json.toJson(historyReply) should be (historyReplyJson)
      }
    }
  }
}

