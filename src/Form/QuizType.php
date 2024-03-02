<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
class QuizType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('experience', ChoiceType::class, [
                'label' => 'Your experience with pets:',
                'choices' => [
                    'Beginner' => 'Beginner',
                    'Intermediate' => 'Intermediate',
                    'Experienced' => 'Experienced',
                ],
                'expanded' => true,
                'multiple' => false,
                'required' => true,
            ])
            ->add('daily_commitment', ChoiceType::class, [
                'label' => 'Daily time commitment:',
                'choices' => [
                    'Low (1-2 hours)' => 'Low (1-2 hours)',
                    'Medium (2-4 hours)' => 'Medium (2-4 hours)',
                    'High (4+ hours)' => 'High (4+ hours)',
                ],
                'expanded' => true,
                'multiple' => false,
                'required' => true,
            ])
            ->add('living_space', ChoiceType::class, [
                'label' => 'Living space available',
                'choices' => [
                    'Apartment' => 'Apartment',
                    'House' => 'House',
                
                ],
                'expanded' => true,
                'multiple' => false,
                'required' => true,
            ])
            ->add('budget', ChoiceType::class, [
                'label' => 'Budget for pet care:',
                'choices' => [
                    'Low' => 'Low',
                    'Medium' => 'Medium',
                    'High' => 'High',
                    
                ],
                'expanded' => true,
                'multiple' => false,
                'required' => true,
            ])
            ->add('allergies', ChoiceType::class, [
                'label' => 'Any allergies in the household:',
                'choices' => [
                    'Yes' => 'Yes',
                    'No' => 'No',
                ],
                'expanded' => true,
                'multiple' => false,
                'required' => true,
            ])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
           
        ]);
    }
}