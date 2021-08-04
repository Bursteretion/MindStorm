import request from '@/utils/request';

export async function queryExamPaper(params) {
  return request('/mindstorm-education/education/exam-paper/query', {
    method: 'POST',
    data: params,
  });
}

export async function createExamPaper(examPaperVO) {
  return request('/mindstorm-education/education/exam-paper/create', {
    method: 'POST',
    data: examPaperVO,
  });
}

export async function updateExamPaper(examPaperVO) {
  return request('/mindstorm-education/education/exam-paper/update', {
    method: 'POST',
    data: examPaperVO,
  });
}

export async function deleteExamPaper(examPaperId) {
  return request(`/mindstorm-education/education/exam-paper/delete/${examPaperId}`, {
    method: 'DELETE',
  });
}

export async function infoExamPaper(examPaperId) {
  return request(`/mindstorm-education/education/exam-paper/info`, {
    method: 'GET',
    params: { examPaperId },
  });
}
