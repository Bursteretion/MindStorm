import request from '@/utils/request';

export async function listQuestionTypes() {
  return request('/mindstorm-education/education/question-type/list/select');
}

export async function createQuestionType(questionTypeVO) {
  return request('/mindstorm-education/education/question-type/create', {
    method: 'POST',
    data: questionTypeVO,
  });
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
