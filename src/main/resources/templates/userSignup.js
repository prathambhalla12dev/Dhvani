/**
 * Function to perform client-side password validation before form submission.
 * @param {Event} event The submit event object.
 * @returns {boolean} True if validation passes (allow submit), false otherwise (block submit).
 */
function validateForm(event) {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm_password').value;
    const errorElement = document.getElementById('passwordError');

    if (password !== confirmPassword) {
        // If passwords don't match, show an error message and prevent submission.
        errorElement.classList.remove('hidden');
        
        // We use preventDefault to stop the form from sending to the server
        event.preventDefault(); 
        return false;
    } else {
        // If they match, ensure the error message is hidden and allow submission.
        errorElement.classList.add('hidden');
        return true;
    }
}