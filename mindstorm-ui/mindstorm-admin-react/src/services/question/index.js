import request from '@/utils/request';

export async function queryQuestion(params) {
  return request('/mindstorm-education/education/question/query', {
    method: 'POST',
    data: params,
  });
}

export async function createQuestion(questionVO) {
  return request('/mindstorm-education/education/question/create', {
    method: 'POST',
    data: questionVO,
  });
}

export async function updateQuestion(questionVO) {
  return request('/mindstorm-education/education/question/update', {
    method: 'POST',
    data: questionVO,
  });
}

export async function deleteQuestion(questionId) {
  return request(`/mindstorm-education/education/question/delete/${questionId}`, {
    method: 'DELETE',
  });
}

export async function infoQuestion(questionId) {
  return request(`/mindstorm-education/education/question/info/${questionId}`);
}

export const QuestionDifficultyStatus = {
  2: { text: '困难', status: 2 },
  1: { text: '中等', status: 1 },
  0: { text: '简单', status: 0 },
};