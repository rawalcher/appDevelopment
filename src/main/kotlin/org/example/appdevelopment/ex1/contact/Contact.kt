data class Contact(
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val age: Int? = null,
    val address: String? = null,
    val company: String? = null,
    val title: String? = null
)

fun main() {
    val contacts = listOf(
        Contact("John Doe").apply {
        },
        Contact("Jane Smith").apply {
        },
        run {
            Contact(
                name = "Mike Johnson",
                email = "mike@example.com",
                phone = "555-1234",
                age = 32,
                address = "123 Main St, Anytown, USA",
                company = "Tech Solutions"
            )
        },
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

    // Process the List with lambda functions

    // Filtering: Get all contacts with a valid email address
    val contactsWithEmail = filterContactsWithEmail(contacts)
    println("Contacts with email:")
    contactsWithEmail.forEach { println("${it.name}: ${it.email}") }
    println()

    // Mapping: Generate a list of strings containing only name and email
    val nameAndEmails = mapContactsToNameAndEmail(contacts)
    println("Names and emails:")
    nameAndEmails.forEach { println(it) }
    println()

    // Accumulation: Calculate the average age
    val averageAge = calculateAverageAge(contacts)
    println("Average age: $averageAge\n")

    // Apply Scope Functions

    processContactWithEmail(contacts[0])
    processContactWithEmail(contacts[2])
    println()

    val configuredContact = createConfiguredContact()
    println("Created contact: ${configuredContact.name}")
    println()

    displayContactDetails(contacts[2])
    println()

    val searchResult = findContactByName(contacts, "Mike Johnson")
    println("Search result: ${searchResult?.name ?: "Not found"}")
    println()

    val contactWithSideEffect = contacts[3].also {
        println("Processing contact: ${it.name}")
    }
    println("Contact after side effect: ${contactWithSideEffect.name}")
}

fun filterContactsWithEmail(contacts: List<Contact>): List<Contact> {
    return contacts.filter { it.email != null }
}

fun mapContactsToNameAndEmail(contacts: List<Contact>): List<String> {
    return contacts.map { "${it.name} - ${it.email ?: "No email"}" }
}

// .mapNotNull returns an empty list when there are no valid non-null ages,
// calling average on that will return NaN
fun calculateAverageAge(contacts: List<Contact>): Double {
    return contacts.mapNotNull { it.age }.average()
}

fun processContactWithEmail(contact: Contact) {
    contact.email?.let {
        println("Sending email to ${contact.name} at $it")
    } ?: println("No email available for ${contact.name}")
}

// since we cant directly modify Contact (immutable) we can just copy values, and create a new one
fun createConfiguredContact(): Contact {
    return Contact("Alex Turner").let { baseContact ->
        baseContact.copy(
            email = "alex@example.com",
            phone = "555-3456",
            age = 31,
            address = "789 Pine St, Elsewhere, USA"
        ).apply {
            // now we can use 'apply' for its intended purpose:
            // performing additional actions and returning the same object
            println("Created contact with name: $name and email: $email")
        }
    }
}

fun displayContactDetails(contact: Contact) {
    with(contact) {
        println("Contact Details:")
        println("Name: $name")
        println("Email: ${email ?: "N/A"}")
        println("Phone: ${phone ?: "N/A"}")
        println("Age: ${age ?: "N/A"}")
        println("Address: ${address ?: "N/A"}")
        println("Company: ${company ?: "N/A"}")
    }
}

fun findContactByName(contacts: List<Contact>, name: String): Contact? {
    return contacts.find { it.name.equals(name, ignoreCase = true) }
}

/**
 * ChatGPT provided TLDR
 *
 * | Function | Context object | Return value | Typical use case |
 * |----------|---------------|--------------|------------------|
 * | let      | it (argument) | Lambda result| Operating on nullable values, transforming objects |
 * | run      | this (receiver)| Lambda result| Object configuration, computing a result from an object |
 * | with     | this (receiver)| Lambda result| Grouping multiple operations on an object |
 * | apply    | this (receiver)| Context object| Object configuration |
 * | also     | it (argument) | Context object| Additional actions, side effects |
 *
 * - let: Executes a block with the object as 'it' and returns the result of the block
 * - run: Executes a block with the object as 'this' and returns the result of the block
 * - with: Non-extension function that takes an object and a lambda, executes the lambda with the object as 'this'
 * - apply: Executes a block with the object as 'this' and returns the object itself
 * - also: Executes a block with the object as 'it' and returns the object itself
 */