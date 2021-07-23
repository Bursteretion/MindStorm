import request from '@/utils/request';

export async function listQuestionTypes() {
  return request('/mindstorm-education/education/question-type/list');
}

export async function listQuestionTypeSelects() {
  return request('/mindstorm-education/education/question-type/list/select');
}

export async function createQuestionType(questionTypeVO) {
  return request('/mindstorm-education/education/question-type/create', {
    method: 'POST',
    data: questionTypeVO,
  });
}

export async function infoQuestionType(questionTypeId) {
  return request(`/mindstorm-education/education/question-type/info/${questionTypeId}`);
}

export async function updateQuestionType(questionTypeVO) {
  return request('/mindstorm-education/education/question-type/update', {
    method: 'POST',
    data: questionTypeVO,
  });
}

export async function deleteQuestionType(questionTypeId) {
  return request(`/mindstorm-education/education/question-type/delete/${questionTypeId}`, {
    method: 'DELETE',
  });
}

export const QuestionType = [
  {
    label: '单选题',
    value: 0,
  },
  {
    label: '多选题',
    value: 1,
  },
  {
    label: '填空题',
    value: 2,
  },
  {
    label: '判断题',
    value: 3,
  },
  {
    label: '简答题',
    value: 4,
  },
];
