import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.propspec.AnyPropSpec
import org.scalatest.refspec.RefSpec
import org.scalatest.wordspec.AnyWordSpec

object ScalaTestingStyles

class CalculatorSuite extends AnyFunSuite {

  val calculator = new Calculator

  test("0을 곱하면 무조건 0이다") {
    assert(calculator.multiply(43802, 0) == 0)
    assert(calculator.multiply(-43802, 0) == 0)
    assert(calculator.multiply(444233802, 0) == 0)
    assert(calculator.multiply(393, 0) == 0)
  }

  test("0으로 나누면 무조건 에러다") {
    assertThrows[ArithmeticException](calculator.divide(3482, 0))
  }
}

// Behavior-Driven-Development, Nested
class CalculatorSpec extends AnyFunSpec {

  val calculator = new Calculator

  describe("multiplication") {
    it("should give back 0 if multiplying by 0") {
      assert(calculator.multiply(43802, 0) == 0)
      assert(calculator.multiply(-43802, 0) == 0)
      assert(calculator.multiply(444233802, 0) == 0)
      assert(calculator.multiply(393, 0) == 0)
    }
  }

  describe("division") {
    it("should throw a math error if dividing by 0") {
      assertThrows[ArithmeticException](calculator.divide(3482, 0))
    }
  }
}

// 읽기 좋은 Nested, Natural Language
class CalculatorWordSpec extends AnyWordSpec {

  val calculator = new Calculator

  "A calculator" should {
    "give back 0 if multiplying by 0" in {
      assert(calculator.multiply(43802, 0) == 0)
      assert(calculator.multiply(-43802, 0) == 0)
      assert(calculator.multiply(444233802, 0) == 0)
      assert(calculator.multiply(393, 0) == 0)
    }

    "Throw a math error if dividing by 0" in {
      assertThrows[ArithmeticException](calculator.divide(3482, 0))
    }
  }
}

// Most Flexible Style
class CalculatorFreeSpec extends AnyFreeSpec {

  val calculator = new Calculator

  // - => anything you want
  "A calculator" - {
    "give back 0 if multiplying by 0" in {
      assert(calculator.multiply(43802, 0) == 0)
      assert(calculator.multiply(-43802, 0) == 0)
      assert(calculator.multiply(444233802, 0) == 0)
      assert(calculator.multiply(393, 0) == 0)
    }

    "Throw a math error if dividing by 0" in {
      assertThrows[ArithmeticException](calculator.divide(3482, 0))
    }
  }
}

// property-style checking
class CalculatorPropSpec extends AnyPropSpec {

  val calculator = new Calculator

  val multiplyByZeroExamples = List((6232, 0), (23901, 0), (0, 0))

  property("Calculator multiply by 0 should be 0") {
    assert(multiplyByZeroExamples.forall{
      case (a, b) => calculator.multiply(a, b) == 0
    })
  }

  property("Calculator divide by 0 should throw error") {
    assertThrows[ArithmeticException](calculator.divide(3482, 0))
  }
}

// based on the reflection
class CalculatorRefSpec extends RefSpec {
  object `A Calculator` {
    // test suite
    val calculator = new Calculator

    def `multiply by 0 should be 0`(): Unit = {
      assert(calculator.multiply(43802, 0) == 0)
      assert(calculator.multiply(-43802, 0) == 0)
      assert(calculator.multiply(444233802, 0) == 0)
      assert(calculator.multiply(393, 0) == 0)
    }

    def `divide by 0 should throw error`: Unit = {
      assertThrows[ArithmeticException](calculator.divide(3482, 0))
    }
  }
}

class Calculator {
  def add(a: Int, b: Int): Int = a + b
  def subtract(a: Int, b: Int): Int = a - b
  def multiply(a: Int, b: Int): Int = a * b
  def divide(a: Int, b: Int): Int = a / b
}