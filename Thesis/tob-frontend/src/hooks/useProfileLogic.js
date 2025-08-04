import { useState, useEffect, useCallback } from "react";
import {
  fetchCurrentUser,
  fetchAllUsers,
  createUser,
  deleteUser,
  updateUser,
} from "../api/usersApi";

export const useProfileLogic = () => {
  const [role, setRole] = useState(null);
  const [loading, setLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [showEditModal, setShowEditModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [currentUserId, setCurrentUserId] = useState(null);

  useEffect(() => {
    fetchCurrentUser()
      .then((res) => setCurrentUserId(res.data.id))
      .catch((err) => console.error("Current user error:", err));
  }, []);

  useEffect(() => {
    const storedRole = localStorage.getItem("role");
    setRole(storedRole || "GUEST");
    setLoading(false);
  }, []);

  const fetchUsers = useCallback(() => {
    if (role === "ADMIN") {
      fetchAllUsers()
        .then((res) => setUsers(res.data))
        .catch((err) => console.error("User fetch error:", err));
    }
  }, [role]);

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  const onRegister = (firstName, lastName, userName, email, password) => {
    const newUser = { firstName, lastName, userName, email, password };
    createUser(newUser)
      .then((res) => setUsers((prev) => [...prev, res.data]))
      .catch((err) => console.error("User create error:", err));
  };

  const handleDeleteUser = (userId) => {
    deleteUser(userId)
      .then(() =>
        setUsers((prev) => prev.filter((user) => user.id !== userId))
      )
      .catch((err) => console.error("User delete error:", err));
  };

  const handleEditClick = (user) => {
    setSelectedUser(user);
    setShowEditModal(true);
  };

  const handleUserUpdate = (updatedUser) => {
    updateUser(updatedUser)
      .then((res) =>
        setUsers((prev) =>
          prev.map((u) => (u.id === updatedUser.id ? res.data : u))
        )
      )
      .catch((err) => console.error("User update error:", err));
  };

  return {
    role,
    loading,
    users,
    showEditModal,
    selectedUser,
    currentUserId,
    onRegister,
    handleDeleteUser,
    handleEditClick,
    handleUserUpdate,
    closeModal: () => setShowEditModal(false),
  };
};
