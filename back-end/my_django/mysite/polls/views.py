from django.http import (
    HttpResponse,
    HttpResponseRedirect)
from django.urls import reverse
from django.shortcuts import render

def index(request):
    #return HttpResponse('main index')
    #return HttpResponseRedirect('1')
    #return HttpResponse(request, "polls/main.html.")
    # return HttpResponseRedirect(
    #     reverse('detail',args=[1]))
    # return HttpResponseRedirect(
    #    reverse('detail',kwargs={'question_id':1}))
    ctx={
        'greetings':'hello there',
        'location':{
            'city':'seoul',
            'country':'south kor'
        },
        'languages':['kor','eng']
    }
    return render(request, 'polls/main.html',context=ctx)

# Create your views here.
def detail(request,question_id):
    return HttpResponse("질문보고 있음 님 %s" % question_id)

def results(request,question_id):
    response='결과를 보고 있음 님 %s'
    return HttpResponse(response % question_id)

def vote(request,question_id):
    return HttpResponse("후기 남기셈 %s" % question_id)