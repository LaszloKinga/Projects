import React from "react";
import ProfileCard from "../ProfilePageComponents/ProfileCard";
import UserTable from "../profilePageComponents/UserTable";
import AdminRegisterPage from "../profilePageComponents/AdminRegisterPage";
import UserEditModal from "../profilePageComponents/UserEditModal";
import UserScores from "../profilePageComponents/UserScores";
import Leaderboard from "../profilePageComponents/Leaderboard";
import styles from "../../styles/ProfileCard.module.css";
import { useProfileLogic } from "../../hooks/useProfileLogic";

const Profile = () => {
  const {
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
    closeModal,
  } = useProfileLogic();

  if (loading) return <div>Loading...</div>;

  return (
    <div className={`${styles.containerStyle} container mt-5`}>
      <div className="d-flex flex-wrap gap-4">
        <div className={`${styles.element} flex-grow-1`}>
          <div className="d-flex flex-column gap-3">
            <ProfileCard />
            {role === "USER" && currentUserId && (
              <>
                <div className={styles.wrapper}>
                  <div className={styles.left}>
                    <div className={styles.box}>
                      <UserScores userId={currentUserId} />
                    </div>
                  </div>
                  <div className={styles.right}>
                    <div className={styles.box}>
                      <Leaderboard />
                    </div>
                  </div>
                </div>
              </>
            )}

            {role === "ADMIN" && (
              <UserTable
                users={users}
                onDelete={handleDeleteUser}
                onEdit={handleEditClick}
              />
            )}
          </div>
        </div>

        <div className={`${styles.element} flex-grow-1`}>
          {role === "ADMIN" && (
            <div className={`${styles.loginComp} card p-4 shadow-sm`}>
              <AdminRegisterPage onRegister={onRegister} />
            </div>
          )}
        </div>
      </div>

      <UserEditModal
        show={showEditModal}
        onClose={closeModal}
        user={selectedUser}
        onUpdate={handleUserUpdate}
      />
    </div>
  );
};

export default Profile;
