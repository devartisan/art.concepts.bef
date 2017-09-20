package com.lss.bef.core

import io.kotlintest.matchers.beOfType
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import lss.bef.core.Do

class DoIdentityKTest : StringSpec() {

	 init  {
		"identityLongTest"{
			val id = Do.getUniqueId()

			println( "On DoIdentityTest long id = [$id]\n" )
            id should beOfType<Long>()
		}

		"identityUniqueIdFromStrTest"{
		 	val testStr = "1y2p0ij32e8e7"
		 	val id : Long = Do.getUniqueIdFromStr( testStr )

		 	println( "On DoIdentityTest long id = [$id] from string [$testStr]\n" )
			testStr shouldBe Do.getUniqueStrFromId( id )
		}

		"identityWeakUniqueIdFromStrTest"{
		 	val testStr = "zik0zj"
			val id : Int = Do.getWeakUniqueIdFromStr( testStr )

		 	println( "On DoIdentityTest int id = [$id] from string [$testStr]\n" )
		 	testStr shouldBe Do.getWeakUniqueStrFromId( id )
		}

         "identityWeakUniqueIdFromHashTest"{
             val testStr1 = "THIS_IS_TEST_STRING"
			 val testStr2 = "THIS_IS_TEST_STRING2"
             val id1 : Int = Do.getWeakUniqueIdFromHash( testStr1 )
             val id2 : Int = Do.getWeakUniqueIdFromHash( testStr2 )

             println( "On DoIdentityTest int id1 = [$id1] from hash string [$testStr1]\n" )
             println( "On DoIdentityTest int id2 = [$id2] from hash string [$testStr2]\n" )
             id1 shouldNotBe id2
         }
	}
}
