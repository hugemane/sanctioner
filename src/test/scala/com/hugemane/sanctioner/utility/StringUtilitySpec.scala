package com.hugemane.sanctioner.utility

import com.hugemane.sanctioner.test.TestBaseSpec
import com.hugemane.sanctioner.utility.StringUtility._

class StringUtilitySpec extends TestBaseSpec {

  it should "have value when string has value" in {
    val value = "the cat in the hat"
    value.has_value() shouldBe true
  }

  it should "not have value when string is empty or blank" in {
    val value = ""
    value.has_value() shouldBe false

    val blankValue = "    "
    blankValue.has_value() shouldBe false
  }

  it should "have value when option string has value" in {
    val value = Some("the cat in the hat")
    value.has_value() shouldBe true
  }

  it should "not have value when option string is empty, none or blank" in {
    val value = None
    value.has_value() shouldBe false

    val blankValue = Some("    ")
    blankValue.has_value() shouldBe false

    val emptyValue = Some("")
    emptyValue.has_value() shouldBe false
  }
}
