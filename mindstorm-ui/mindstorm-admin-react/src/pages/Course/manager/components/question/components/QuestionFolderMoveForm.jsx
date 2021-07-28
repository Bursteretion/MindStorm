import React, { useEffect, useState } from 'react';
import { message, Modal, Skeleton, Tree } from 'antd';
import { listTreeFolders, moveQuestion } from '@/services/question';

const { DirectoryTree } = Tree;

const QuestionFolderMoveForm = (props) => {
  const { isModalVisible, setModalVisible, courseId, actionRef, questionId, questionPid } = props;
  const [treeFolders, setTreeFolders] = useState(undefined);
  const [currentFolder, setCurrentFolder] = useState(undefined);
  const [selectedFolder, setSelectedFolder] = useState(undefined);

  const findCurrentFolder = (folders = []) => {
    if (folders.length === 0) return false;
    // eslint-disable-next-line no-plusplus
    for (let i = 0; i < folders.length; i++) {
      if (folders[i].key === questionId) {
        setCurrentFolder(folders[i]);
        return true;
      }
      if (findCurrentFolder(folders[i].children)) {
        return true;
      }
    }
    return false;
  };

  useEffect(() => {
    async function fetchFolder() {
      if (courseId !== undefined) {
        const res = await listTreeFolders({ courseId });
        if (res.success) {
          const fatherFolder = { key: '0', title: '根目录' };
          fatherFolder.children = res.data.treeFolders;
          setTreeFolders([fatherFolder]);
          findCurrentFolder([fatherFolder]);
        }
      }
    }

    fetchFolder();
  }, [courseId]);

  const canMove = (up = true, folder) => {
    if (up) {
      const { key } = folder.key;
      const { children = [] } = folder;
      for (let i = 0; i < children.length; i++) {
        if (children[i].key === key) {
          message.warn('不能将父文件夹移动至子文件夹中！');
          return false;
        }
        if (canMove(up, children[i])) {
          return true;
        }
      }
    } else {
      const res = folder.key === currentFolder.pid;
      if (res) {
        message.warn('该文件夹/题目已在要移动到的目标文件夹中');
        return false;
      }
      return true;
    }
    return false;
  };

  const handleMove = async () => {
    const { key } = selectedFolder;
    if (key === undefined) {
      message.info('请选择目标文件夹');
      return;
    }
    if (questionId === key) {
      message.warn('不能移动至本身！');
      return;
    }
    if (currentFolder !== undefined) {
      if (!canMove(true, currentFolder) && !canMove(false, selectedFolder)) {
        return;
      }
    } else if (selectedFolder.key === questionPid) {
      message.warn('该文件夹/题目已在要移动到的目标文件夹中');
      return;
    }
    console.log(currentFolder, selectedFolder, questionPid);

    const hide = message.loading('正在移动.....');
    const res = await moveQuestion({ questionId, moveToPid: selectedFolder.key });
    hide();
    if (res.success) {
      message.success('移动成功！');
      actionRef.current.reset();
      setModalVisible(false);
    } else {
      message.error(res.message);
    }
  };

  const onSelect = (keys, info) => {
    setSelectedFolder({ key: keys[0] });
    setSelectedFolder(info.node);
  };

  return (
    <>
      <Modal
        style={{ top: 40 }}
        okText="确定"
        destroyOnClose={true}
        maskClosable={false}
        width={500}
        title="移动至"
        visible={isModalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => {
          handleMove();
        }}
      >
        {treeFolders === undefined ? (
          <Skeleton active />
        ) : (
          <DirectoryTree onSelect={onSelect} treeData={treeFolders} />
        )}
      </Modal>
    </>
  );
};

export default QuestionFolderMoveForm;
