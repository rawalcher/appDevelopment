package org.example.appdevelopment.ex1.contact

import Contact
import calculateAverageAge
import createConfiguredContact
import filterContactsWithEmail
import findContactByName
import mapContactsToNameAndEmail
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ContactTest {

    private val testContacts = listOf(
        Contact("John Doe"),
        Contact("Jane Smith", email = "jane@example.com"),
        Contact(
            name = "Mike Johnson",
            email = "mike@example.com",
            phone = "555-1234",
            age = 32,
            address = "123 Main St, Anytown, USA",
            company = "Tech Solutions"
        ),
        Contact(
            name = "Sarah Williams",
            email = "sarah@example.com",
            phone = "555-5678",
            age = 28,
            company = "Design Studio"
        ),
        Contact(
            name = "David Brown",
            email = null,
            phone = "555-9012",
            age = 45,
            address = "456 Oak Ave, Somewhere, USA"
        )
    )

    @Test
    fun testDataClassProperties() {
        val contact = Contact(
            name = "Test User",
            email = "test@example.com",
            phone = "123-456-7890",
            age = 30,
            address = "Test Address",
            company = "Test Company",
            title = "Test Title"
        )

        assertEquals("Test User", contact.name)
        assertEquals("test@example.com", contact.email)
        assertEquals("123-456-7890", contact.phone)
        assertEquals(30, contact.age)
        assertEquals("Test Address", contact.address)
        assertEquals("Test Company", contact.company)
        assertEquals("Test Title", contact.title)
    }

    @Test
    fun testDataClassEquality() {
        val contact1 = Contact("Same", "same@example.com", "123-456-7890")
        val contact2 = Contact("Same", "same@example.com", "123-456-7890")
        val contact3 = Contact("Different", "different@example.com")

        assertEquals(contact1, contact2)
        assertNotEquals(contact1, contact3)
    }

    @Test
    fun testDataClassCopy() {
        val original = Contact("Original", "original@example.com")
        val copied = original.copy(name = "Copied")

        assertEquals("Copied", copied.name)
        assertEquals("original@example.com", copied.email)
        assertNotEquals(original, copied)
    }

    @Test
    fun testFilterContactsWithEmail() {
        val filteredContacts = filterContactsWithEmail(testContacts)

        assertEquals(3, filteredContacts.size)

        assertTrue(filteredContacts.all { it.email != null })

        assertTrue(filteredContacts.any { it.name == "Jane Smith" })
        assertTrue(filteredContacts.any { it.name == "Mike Johnson" })
        assertTrue(filteredContacts.any { it.name == "Sarah Williams" })
        assertFalse(filteredContacts.any { it.name == "John Doe" })
        assertFalse(filteredContacts.any { it.name == "David Brown" })
    }

    @Test
    fun testMapContactsToNameAndEmail() {
        val mappedContacts = mapContactsToNameAndEmail(testContacts)

        assertEquals(testContacts.size, mappedContacts.size)

        assertTrue(mappedContacts.contains("John Doe - No email"))
        assertTrue(mappedContacts.contains("Jane Smith - jane@example.com"))
        assertTrue(mappedContacts.contains("Mike Johnson - mike@example.com"))
        assertTrue(mappedContacts.contains("David Brown - No email"))
    }

    @Test
    fun testCalculateAverageAge() {
        val averageAge = calculateAverageAge(testContacts)

        val expected = 35.0
        assertEquals(expected, averageAge)
    }

    @Test
    fun testCalculateAverageAgeWithEmptyList() {
        val emptyList = emptyList<Contact>()

        val result = calculateAverageAge(emptyList)
        assertTrue(result.isNaN())
    }

    @Test
    fun testCalculateAverageAgeWithNoAges() {
        val noAgeContacts = listOf(
            Contact("No Age 1"),
            Contact("No Age 2")
        )
        val result = calculateAverageAge(noAgeContacts)
        assertTrue(result.isNaN())
    }

    @Test
    fun testFindContactByName() {
        // Test finding existing contact
        val foundContact = findContactByName(testContacts, "Mike Johnson")
        assertNotNull(foundContact)
        assertEquals("Mike Johnson", foundContact?.name)
        assertEquals("mike@example.com", foundContact?.email)

        // Test finding with case insensitivity
        val caseInsensitiveFound = findContactByName(testContacts, "mike johnson")
        assertNotNull(caseInsensitiveFound)
        assertEquals("Mike Johnson", caseInsensitiveFound?.name)

        // Test finding non-existent contact
        val notFound = findContactByName(testContacts, "Nonexistent Person")
        assertNull(notFound)
    }

    @Test
    fun testCreateConfiguredContact() {
        val contact = createConfiguredContact()

        assertEquals("Alex Turner", contact.name)
        assertEquals("alex@example.com", contact.email)
        assertEquals("555-3456", contact.phone)
        assertEquals(31, contact.age)
        assertEquals("789 Pine St, Elsewhere, USA", contact.address)
    }
}