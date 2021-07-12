import { useState, useCallback } from 'react';

const processAcademyTree = (tree = []) => {
  tree.forEach((item) => {
    const backup = item;
    backup.title = item.name;
    backup.value = item.id;
    backup.key = item.id;
    if (item.children && item.children.length <= 0) {
      backup.children = undefined;
    } else {
      processAcademyTree(backup.children);
    }
  });
  return tree;
};

export default () => {
  const [academyTree, setAcademyTree] = useState([]);

  const generateAcademyTreeSelect = useCallback(() => {
    setAcademyTree((tree) => processAcademyTree(tree));
  }, []);

  return {
    academyTree,
    setAcademyTree,
    generateAcademyTreeSelect,
  };
};
