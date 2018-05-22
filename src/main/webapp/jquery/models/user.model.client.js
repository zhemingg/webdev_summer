function User(username, password, firstName, lastName, email, phone, dateOfBirth) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.dateOfBirth = dateOfBirth;

    this.setUsername = setUsername;
    this.getUsername = getUsername;

    this.setPassword = setPassword;
    this.getPassword = getPassword;

    this.setFirstName = setFirstName;
    this.getFirstName = getFirstName;

    this.setLastName = setLastName;
    this.getLastName = getLastName;

    this.setEmail = setEmail;
    this.getEmail = getEmail;

    this.setPhone = setPhone;
    this.getPhone = getPhone;

    this.setDateOfBirth = setDateOfBirth;
    this.getDateOfBirth = getDateOfBirth;

    function setUsername(username) {
        this.username = username;
    }

    function getUsername() {
        return this.username;
    }

    function setPassword() {
        this.password = password;
    }

    function getPassword() {
        return this.password;
    }

    function setFirstName() {
        this.firstName = firstName;
    }

    function getFirstName() {
        return this.firstName;
    }

    function setLastName() {
        this.lastName = lastName;
    }

    function getLastName() {
        return this.lastName;
    }

    function setEmail() {
        this.email = email;
    }

    function getEmail() {
        return this.email();
    }

    function setPhone() {
        this.phone = phone;
    }

    function getPhone() {
        return this.phone;
    }

    function setDateOfBirth() {
        this.dateOfBirth = dateOfBirth;
    }
    
    function getDateOfBirth() {
        return this.dateOfBirth;
    }

}
