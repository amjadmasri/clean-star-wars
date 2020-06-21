# Project Title Star Wars Challenge
## Architecture components used -Room (with rxJava extension) -Navigation -Paging -LiveData -ViewModel
### Architecture and Third party libraries
The architecture of the project follows the principles of Clean Architecture 
the application uses "Use Case" approach for domain and business logic. with the principles of use case composition using RXJava in a functional Style where low level use cases interact with repositories for network and IO operations, and high level use cases are composed of those low level use cases.
the aim of the architecture is to constantly improve my coding to be more
- Testable
 - Scalable
 - Modular
 - Reusable