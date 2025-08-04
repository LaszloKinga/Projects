import { Modal, Button, Form } from "react-bootstrap";
import styles from "../../styles/modalStyles.module.css";
import { useUserEditForm } from "../../hooks/useUserEditForm";

const UserEditModal = ({ show, onClose, user, onUpdate }) => {
  const { formData, handleChange, handleSubmit } = useUserEditForm(user, onUpdate, onClose);

  return (
    <Modal show={show} onHide={onClose} >
      <Modal.Header>
        <Modal.Title className={styles.modalColor}>Edit User</Modal.Title>
      </Modal.Header>
      <Modal.Body className={styles.modalColor}>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>First name</Form.Label>
            <Form.Control
              type="text"
              name="firstName"
              value={formData.firstName || ""}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Last name</Form.Label>
            <Form.Control
              type="text"
              name="lastName"
              value={formData.lastName || ""}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>User name</Form.Label>
            <Form.Control
              type="text"
              name="userName"
              value={formData.userName || ""}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="text"
              name="email"
              value={formData.email || ""}
              onChange={handleChange}
            />
          </Form.Group>

          <Modal.Footer>
            <Button variant="primary" type="submit" className="me-auto">
              Save
            </Button>
            <Button variant="secondary" onClick={onClose}>
              Close
            </Button>
          </Modal.Footer>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default UserEditModal;
